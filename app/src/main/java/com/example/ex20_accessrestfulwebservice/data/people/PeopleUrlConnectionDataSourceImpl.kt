/*
 * Copyright (c) 2022-2024 Universitat Politècnica de València
 * Authors: David de Andrés and Juan Carlos Ruiz
 *          Fault-Tolerant Systems
 *          Instituto ITACA
 *          Universitat Politècnica de València
 *
 * Distributed under MIT license
 * (See accompanying file LICENSE.txt)
 */

package com.example.ex20_accessrestfulwebservice.data.people

import com.example.ex20_accessrestfulwebservice.data.model.PeopleDto
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonDataException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okio.buffer
import okio.source
import java.io.IOException
import java.net.HttpURLConnection.HTTP_OK
import java.net.URL
import javax.inject.Inject
import javax.net.ssl.HttpsURLConnection

/**
 * Data source that provides access to the Random User generator API using UrlConnection.
 * It implements the PeopleDataSource interface.
 */
// @Inject enables Hilt to provide the required dependencies
class PeopleUrlConnectionDataSourceImpl @Inject constructor(
    private val url: URL,
    private val moshiAdapter: JsonAdapter<PeopleDto>
) : PeopleDataSource {

    /**
     * Returns 10 randomly generated person using HttpsUrlConnection or
     * IOException if something goes wrong.
     */
    override suspend fun getPeople(): Result<PeopleDto> =
        // Execute the operation in an IO thread (this is main-safe)
        withContext(Dispatchers.IO) {
            var result: Result<PeopleDto>
            lateinit var connection: HttpsURLConnection
            try {
                // Create a new instance of UrlConnection
                connection = url.openConnection() as HttpsURLConnection
                // Select the GET request method
                connection.requestMethod = "GET"
                // Expect an incoming result
                connection.doInput = true
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