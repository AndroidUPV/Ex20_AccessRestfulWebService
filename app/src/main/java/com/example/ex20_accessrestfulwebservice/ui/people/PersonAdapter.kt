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

package com.example.ex20_accessrestfulwebservice.ui.people

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ex20_accessrestfulwebservice.R
import com.example.ex20_accessrestfulwebservice.databinding.ItemPersonBinding
import com.example.ex20_accessrestfulwebservice.model.Person

class PersonAdapter : ListAdapter<Person, PersonAdapter.PersonViewHolder>(PersonDiffCallback) {

    /**
     * Computes the diff between two persons in the array.
     */
    object PersonDiffCallback : DiffUtil.ItemCallback<Person>() {
        /**
         * Determines whether two persons are the same
         * (let's assume that their name and phone number can identify them).
         */
        override fun areItemsTheSame(oldItem: Person, newItem: Person): Boolean =
            oldItem.name == newItem.name && oldItem.phone == newItem.phone

        /**
         * Determines whether two persons have the same data.
         */
        override fun areContentsTheSame(oldItem: Person, newItem: Person): Boolean =
            oldItem == newItem
    }

    /**
     * Holds a reference to a ViewBinding.
     */
    class PersonViewHolder(private val binding: ItemPersonBinding) :
        RecyclerView.ViewHolder(binding.root) {

        /**
         * Fills the elements within the View with provided Person object.
         */
        fun bind(person: Person) {
            binding.apply {

                tvName.text = person.name
                tvAddress.text = person.address
                tvCity.text = person.city
                tvCountry.text = person.country
                tvEmail.text = person.email
                tvPhone.text = person.phone
                // Glide gets an image from Internet using a background thread and
                // updates the selected View when available
                Glide
                    .with(ivPicture)
                    .load(person.picture)
                    .circleCrop()
                    .placeholder(R.drawable.unknown_person)
                    .into(ivPicture)
            }
        }
    }

    /**
     * Creates the ViewBinding and attaches it to a ViewHolder
     * to easily access all the elements within the View.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonViewHolder =
        PersonViewHolder(
            ItemPersonBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    /**
     * Fills the elements within the View with the required data from the array.
     */
    override fun onBindViewHolder(holder: PersonViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}