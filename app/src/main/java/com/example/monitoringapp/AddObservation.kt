package com.example.monitoringapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.monitoringapp.data.PersonalObservationData
import com.example.monitoringapp.databinding.FragmentAddObservationBinding
import androidx.navigation.fragment.findNavController

class AddObservation : Fragment() {
    //private lateinit var recyclerView: RecyclerView
    //private var speciesNamesList = mutableListOf<String>()

    private val viewModel: PersonalObservationsViewModel by viewModels()
    private lateinit var binding: FragmentAddObservationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //postToList()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddObservationBinding.inflate(layoutInflater, container, false)

        binding.lifecycleOwner = viewLifecycleOwner

        /*binding.chooseSpeciesButton.setOnClickListener() {
            Navigation.findNavController(binding.root).navigate(R.id.action_addObservation_to_speciesList)
        }*/

        binding.submitButton.setOnClickListener {
            addObservation()
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val layoutManager = LinearLayoutManager(context)
        /*recyclerView = view.findViewById(R.id.recycler_view2)
        recyclerView.layoutManager = layoutManager
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = SpeciesListRecyclerAdapter(speciesNamesList)*/
    }

    private fun addObservation(){
        val observation = PersonalObservationData(
            date = binding.dateField.text.toString(),
            hour = binding.startTimeField.text.toString(),
            location = binding.locationField.text.toString(),
            notes = binding.commentField.text.toString(),
            duration = binding.minutesField.text.toString(),
            species = binding.speciesField.text.toString(),
            speciesDetails = binding.speciesDetailsField.text.toString()
        )

        if (observation.date.isEmpty() ||
            observation.hour.isEmpty() ||
            observation.location.isEmpty() ||
            observation.duration.isEmpty() ||
            observation.species.isEmpty() ||
            observation.speciesDetails.isEmpty()) {
            Toast.makeText(context, "You can't have empty fields", Toast.LENGTH_SHORT).show()
            return
        }else{
            viewModel.addObservation(observation)
            findNavController().navigateUp()
        }
    }

    /*private fun addToList(species_names: String){
        speciesNamesList.add(species_names)
    }*/

    /*private fun postToList(){
        for(i in 1..9){
            addToList("Species name $i")
        }
    }*/
}