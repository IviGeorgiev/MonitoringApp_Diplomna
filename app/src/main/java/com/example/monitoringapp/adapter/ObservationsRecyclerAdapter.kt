package com.example.monitoringapp.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.monitoringapp.model.ObservationData
import com.example.monitoringapp.databinding.ItemLayoutBinding

class ObservationsRecyclerAdapter (private var observations: List<ObservationData> = emptyList()) :
    RecyclerView.Adapter<ObservationsRecyclerAdapter.ObservationViewHolder>(){

    private var onItemClickListener: ((ObservationData) -> Unit)? = null

    fun setOnItemClickListener(listener: (ObservationData) -> Unit) {
        onItemClickListener = listener
    }

    class ObservationViewHolder(val binding: ItemLayoutBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ObservationViewHolder {
        val view = ItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ObservationViewHolder(view)
    }

    override fun onBindViewHolder(holder: ObservationViewHolder, position: Int) {
        val individualObservation = observations[position]
        holder.binding.individualObservation = individualObservation

        holder.itemView.setOnClickListener {
            onItemClickListener?.invoke(individualObservation)
        }

        Log.d("ObservationAdapter", "Observations: $observations")

    }

    override fun getItemCount(): Int {
        return observations.size
    }

    fun updateObservations(newObservations: List<ObservationData>) {
        observations = newObservations
        notifyDataSetChanged()
    }
}