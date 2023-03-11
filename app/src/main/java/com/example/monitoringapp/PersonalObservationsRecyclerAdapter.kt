package com.example.monitoringapp

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.monitoringapp.data.PersonalObservationData
import com.example.monitoringapp.databinding.ItemLayoutBinding

class PersonalObservationsRecyclerAdapter (private var observations: List<PersonalObservationData> = emptyList()) :
    RecyclerView.Adapter<PersonalObservationsRecyclerAdapter.PersonalObservationViewHolder>(){

    private var onItemClickListener: ((PersonalObservationData) -> Unit)? = null

    fun setOnItemClickListener(listener: (PersonalObservationData) -> Unit) {
        onItemClickListener = listener
    }

    class PersonalObservationViewHolder(val binding: ItemLayoutBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):PersonalObservationViewHolder {
        val v = ItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PersonalObservationViewHolder(v)
    }

    override fun onBindViewHolder(holder: PersonalObservationViewHolder, position: Int) {
        val personalObservation = observations[position]
        holder.binding.personalObservation = personalObservation

        holder.itemView.setOnClickListener {
            onItemClickListener?.invoke(personalObservation)
        }

        Log.d("PersonalAdapter", "Observations: $observations")

    }

    override fun getItemCount(): Int {
        return observations.size
    }

    fun updateObservations(newObservations: List<PersonalObservationData>) {
        observations = newObservations
        notifyDataSetChanged()
    }
}