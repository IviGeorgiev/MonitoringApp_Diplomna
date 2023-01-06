package com.example.monitoringapp

import android.hardware.biometrics.BiometricManager
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView

class SpeciesListRecyclerAdapter (private var species_names: List<String>) :
    RecyclerView.Adapter<SpeciesListRecyclerAdapter.ViewHolder>(){

    //RecyclerView.ViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.species_item_layout, parent, false)
        //return RecyclerView.ViewHolder(v)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemSpeciesNames.text = species_names[position]
    }

    override fun getItemCount(): Int {
        return species_names.size
    }

    //inner class
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        val itemSpeciesNames: TextView = itemView.findViewById(R.id.tv_species_name)

        init{
            itemView.setOnClickListener{v: View->
                val position: Int = adapterPosition
                Toast.makeText(itemView.context, "You clicked on # ${position+1}", Toast.LENGTH_SHORT).show()
            }
        }
    }
}