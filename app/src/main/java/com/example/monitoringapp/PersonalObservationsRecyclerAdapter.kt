package com.example.monitoringapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.monitoringapp.data.PersonalObservationData
import com.example.monitoringapp.databinding.ItemLayoutBinding

class PersonalObservationsRecyclerAdapter (private val observationList: LiveData<List<PersonalObservationData>>) :
    RecyclerView.Adapter<PersonalObservationsRecyclerAdapter.PersonalObservationViewHolder>(){

    class PersonalObservationViewHolder(val binding: ItemLayoutBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):PersonalObservationViewHolder {
        //val v = LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false)
        //return RecyclerView.ViewHolder(v)

        val v = ItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PersonalObservationViewHolder(v)
    }

    override fun onBindViewHolder(holder: PersonalObservationViewHolder, position: Int) {
        val personalObservation = observationList.value?.get(position)
        holder.binding.personalObservation = personalObservation

    }

    override fun getItemCount(): Int {
        return observationList.value?.size ?: 0
    }
}