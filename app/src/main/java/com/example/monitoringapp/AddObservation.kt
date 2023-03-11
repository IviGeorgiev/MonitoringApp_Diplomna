package com.example.monitoringapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.monitoringapp.data.PersonalObservationData
import com.example.monitoringapp.databinding.FragmentAddObservationBinding

class AddObservation : Fragment() {
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
        val binding = FragmentAddObservationBinding.inflate(layoutInflater, container, false)

        binding.lifecycleOwner = viewLifecycleOwner

        /*binding.chooseSpeciesButton.setOnClickListener() {
            Navigation.findNavController(binding.root).navigate(R.id.action_addObservation_to_speciesList)
        }*/

        return binding.root
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
}