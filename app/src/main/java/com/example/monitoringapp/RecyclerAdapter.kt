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

class RecyclerAdapter (private var list_ids: List<String>, private var dates: List<String>, private var hours: List<String>, private var locations: List<String>, private var species: List<String>) :
    RecyclerView.Adapter<RecyclerAdapter.ViewHolder>(){

    //RecyclerView.ViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false)
        //return RecyclerView.ViewHolder(v)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemListId.text = list_ids[position]
        holder.itemDate.text = dates[position]
        holder.itemHour.text = hours[position]
        holder.itemLocation.text = locations[position]
        holder.itemSpecies.text = species[position]
    }

    override fun getItemCount(): Int {
        return dates.size
    }


    //inner class
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val itemListId: TextView = itemView.findViewById(R.id.tv_listid)
        val itemDate: TextView = itemView.findViewById(R.id.tv_date)
        val itemHour: TextView = itemView.findViewById(R.id.tv_hour)
        val itemLocation: TextView = itemView.findViewById(R.id.tv_location)
        val itemSpecies: TextView = itemView.findViewById(R.id.tv_species)

        init{
            itemView.setOnClickListener{v: View->
                val position: Int = adapterPosition
                Toast.makeText(itemView.context, "You clicked on # ${position+1}", Toast.LENGTH_SHORT).show()
            }
        }
    }
}