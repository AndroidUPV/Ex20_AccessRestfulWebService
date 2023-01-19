/*
 * Copyright (c) 2022
 * David de Andrés and Juan Carlos Ruiz
 * Development of apps for mobile devices
 * Universitat Politècnica de València
 */

package com.example.ex20_accessrestfulwebservice.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.ex20_accessrestfulwebservice.R
import com.example.ex20_accessrestfulwebservice.databinding.ItemPersonBinding
import com.example.ex20_accessrestfulwebservice.model.Person

class PersonAdapter : ListAdapter<Person, PersonAdapter.PersonViewHolder>(PersonDiffCallback) {

    object PersonDiffCallback : DiffUtil.ItemCallback<Person>() {
        override fun areItemsTheSame(oldItem: Person, newItem: Person): Boolean =
            oldItem.cell == newItem.cell && oldItem.email == newItem.email

        override fun areContentsTheSame(oldItem: Person, newItem: Person): Boolean =
            oldItem == newItem
    }

    class PersonViewHolder(private val binding: ItemPersonBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(person: Person) {
            binding.apply {
                tvTitle.text = person.name.title
                tvFirstName.text = person.name.first
                tvSecondName.text = person.name.last
                tvAddress.text = tvAddress.context.getString(
                    R.string.address_number_name,
                    person.location.street.number,
                    person.location.street.name
                )
                tvCity.text = tvCity.context.getString(
                    R.string.address_city_state_zipcode,
                    person.location.city,
                    person.location.state,
                    person.location.postcode
                )
                tvCountry.text = person.location.country
                tvEmail.text = person.email
                tvPhone.text = person.cell
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonViewHolder =
        PersonViewHolder(
            ItemPersonBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: PersonViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}