package com.example.monitoringapp

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.monitoringapp.data.ObservationData
import com.example.monitoringapp.databinding.ItemLayoutBinding

class ObservationsRecyclerAdapter (private var observations: List<ObservationData> = emptyList()) :
    RecyclerView.Adapter<ObservationsRecyclerAdapter.PersonalObservationViewHolder>(){

    private var onItemClickListener: ((ObservationData) -> Unit)? = null

    fun setOnItemClickListener(listener: (ObservationData) -> Unit) {
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

    fun updateObservations(newObservations: List<ObservationData>) {
        observations = newObservations
        notifyDataSetChanged()
    }
}