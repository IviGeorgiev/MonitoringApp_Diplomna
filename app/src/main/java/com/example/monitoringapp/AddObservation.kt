package com.example.monitoringapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class AddObservation : Fragment() {

    private lateinit var button: ImageButton
    private lateinit var recyclerView: RecyclerView

    private var speciesNamesList = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        postToList()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_add_observation, container, false)

        val button = view.findViewById<ImageButton>(R.id.choose_species_button)
        button.setOnClickListener() {
            Navigation.findNavController(view).navigate(R.id.action_addObservation_to_speciesList)
        }
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
        for(i in 1..9){
            addToList("Species name $i")
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
    }
}