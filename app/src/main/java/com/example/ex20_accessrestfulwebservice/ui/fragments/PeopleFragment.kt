package com.example.ex20_accessrestfulwebservice.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.ex20_accessrestfulwebservice.R
import com.example.ex20_accessrestfulwebservice.databinding.FragmentPeopleBinding
import com.example.ex20_accessrestfulwebservice.ui.adapters.PersonAdapter

class PeopleFragment : Fragment(R.layout.fragment_people) {

    private var _binding: FragmentPeopleBinding? = null
    private val binding
        get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentPeopleBinding.bind(view)

        binding.recyclerView.adapter = PersonAdapter()
    }
}