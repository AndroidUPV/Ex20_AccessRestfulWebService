/*
 * Copyright (c) 2022
 * David de Andrés and Juan Carlos Ruiz
 * Development of apps for mobile devices
 * Universitat Politècnica de València
 */

package com.example.ex20_accessrestfulwebservice.data.datasources

import android.net.Uri
import com.example.ex20_accessrestfulwebservice.model.People
import com.squareup.moshi.JsonDataException
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okio.buffer
import okio.source
import java.io.IOException
import java.net.HttpURLConnection.HTTP_OK
import java.net.URL
import javax.net.ssl.HttpsURLConnection

/**
 * Data source that provides access to the Random User generator API using UrlConnection.
 * It implements the PeopleDataSource interface and the Singleton pattern.
 */
// Just by declaring object instead of class we have a Singleton. Only valid for empty constructors.
object UrlConnectionPeopleDataSourceImpl : PeopleDataSource {

    // URL to get 10 persons with random name, location, email address, phone number, and picture
    // in json format, and excluding seed, result, page, and version data
    private val url = URL(
        Uri.Builder()
            .scheme("https")
            .authority("randomuser.me")
            .appendPath("api")
            .appendQueryParameter("results", "10")
            .appendQueryParameter("inc", "name,location,email,cell,picture")
            .appendQueryParameter("format", "json")
            .appendQueryParameter("", "noinfo")
            .build()
            .toString()
    )

    // Moshi adapter to convert a People object from/to JSON
    private val moshiAdapter = Moshi.Builder()
        .addLast(KotlinJsonAdapterFactory())
        .build()
        .adapter(People::class.java)

    /**
     * Returns 10 randomly generated person using HttpsUrlConnection or
     * IOException if something goes wrong.
     */
    override suspend fun getPeople(): Result<People> =
        // Execute the operation in an IO thread (this is main-safe)
        withContext(Dispatchers.IO) {
            var result: Result<People>
            lateinit var connection: HttpsURLConnection
            try {
                // Create a new instance of UrlConnection
                connection = url.openConnection() as HttpsURLConnection
                // Select the GET request method
                connection.requestMethod = "GET"
                // Expect an incoming result
                connection.doInput = true
                // Establish the connection
                connection.connect()
                // If the request was successful (HTTP 200) then
                // transform the JSON response in a People class using the Moshi adapter
                result = if (connection.responseCode == HTTP_OK) {
                    val people = moshiAdapter.fromJson(connection.inputStream.source().buffer())
                    if (people != null) {
                        Result.success(people)
                    } else {
                        // Return an JsonDataException to signal that something went wrong (result should not be null)
                        Result.failure(JsonDataException())
                    }
                } else {
                    // Return an IOException to signal that something went wrong
                    Result.failure(IOException())
                }

            } catch (jde: JsonDataException) {
                // Return an JsonDataException to signal that something went wrong
                result = Result.failure(jde)

            } catch (ioe: IOException) {
                // Return an IOException to signal that something went wrong
                result = Result.failure(ioe)
            } finally {
                // Ensure that the connection is closed even when something went wrong
                connection.disconnect()
            }
            return@withContext result
        }
}