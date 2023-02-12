/*
 * Copyright (c) 2022
 * David de Andrés and Juan Carlos Ruiz
 * Development of apps for mobile devices
 * Universitat Politècnica de València
 */

package com.example.ex20_accessrestfulwebservice.ui.fragments

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import com.example.ex20_accessrestfulwebservice.R
import com.example.ex20_accessrestfulwebservice.data.repositories.ConnectionLibrary
import com.example.ex20_accessrestfulwebservice.databinding.FragmentPeopleBinding
import com.example.ex20_accessrestfulwebservice.ui.adapters.PersonAdapter
import com.example.ex20_accessrestfulwebservice.ui.viewmodels.PeopleViewModel
import com.example.ex20_accessrestfulwebservice.utils.NoInternetException
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import java.io.IOException

/**
 * Displays a list of people (in a RecyclerView).
 * The FloatingActionButton gets and adds 10 more people to the list.
 * Action elements enable Retrofit or UrlConnection to be selected as connection libraries.
 */
// The Hilt annotation @AndroidEntryPoint is required to receive dependencies from its parent class
@AndroidEntryPoint
class PeopleFragment : Fragment(R.layout.fragment_people), MenuProvider {

    // Reference to the ViewModel
    private val viewModel: PeopleViewModel by viewModels()

    // Backing property to resource binding
    private var _binding: FragmentPeopleBinding? = null

    // Property valid between onCreateView() and onDestroyView()
    private val binding
        get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Get the automatically generated view binding for the layout resource
        _binding = FragmentPeopleBinding.bind(view)

        // Add this Fragment as MenuProvider to the MainActivity
        requireActivity().addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)

        // Adapter for the RecyclerView with Vertical LinearLayoutManager
        val adapter = PersonAdapter()
        binding.recyclerView.adapter = adapter

        // Submit a new list to be displayed whenever the list of persons changes
        viewModel.people.observe(viewLifecycleOwner) { people ->
            adapter.submitList(people.people)
        }
        // Display a message error when there is some problem retrieving the information from the web service
        viewModel.error.observe(viewLifecycleOwner) { error ->
            if (error != null) {
                val message = when (error) {
                    is NoInternetException -> R.string.no_internet
                    is IOException -> R.string.network_error
                    else -> R.string.unknown_error
                }
                Snackbar.make(binding.recyclerView, message, Snackbar.LENGTH_SHORT)
                    .show()
                viewModel.resetError()
            }
        }
        // Update the action elements according to the selected connection library
        viewModel.connectionLibrary.observe(viewLifecycleOwner) {
            requireActivity().invalidateMenu()
        }

        // Get more people to display in the list
        binding.fabMorePeople.setOnClickListener {
            viewModel.getMorePeople()
        }
    }

    // Populates the ActionBar with action elements
    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.menu_connection, menu)
    }

    // Allows the modification of elements of the already created menu before showing it
    override fun onPrepareMenu(menu: Menu) {
        menu.findItem(R.id.mRetrofit).isVisible =
            viewModel.connectionLibrary.value != ConnectionLibrary.RETROFIT
        menu.findItem(R.id.mUrlConnection).isVisible =
            viewModel.connectionLibrary.value != ConnectionLibrary.HTTPS_URL_CONNECTION
        super.onPrepareMenu(menu)
    }

    // Reacts to the selection of action elements
    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        // Determine the action to take place according to its Id
        return when (menuItem.itemId) {

            // Set Retrofit as the selected connection library
            R.id.mRetrofit -> {
                viewModel.setConnectionLibrary(ConnectionLibrary.RETROFIT)
                true
            }

            // Set UrlConnection as the selected connection library
            R.id.mUrlConnection -> {
                viewModel.setConnectionLibrary(ConnectionLibrary.HTTPS_URL_CONNECTION)
                true
            }

            // If none of the custom action elements was selected, let the system deal with it
            else -> false
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}