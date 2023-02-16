package com.example.monitoringapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class SpeciesList : Fragment() {
    private lateinit var recyclerView: RecyclerView

    private var speciesNamesList = mutableListOf<String>()

    private lateinit var button: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        postToList()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_species_list, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val layoutManager = LinearLayoutManager(context)
        recyclerView = view.findViewById(R.id.recycler_view2)
        recyclerView.layoutManager = layoutManager
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = SpeciesListRecyclerAdapter(speciesNamesList)
    }

    private fun addToList(species_names: String){
        speciesNamesList.add(species_names)
    }

    private fun postToList(){
        for(i in 1..19){
            addToList("Species name $i")
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }
}