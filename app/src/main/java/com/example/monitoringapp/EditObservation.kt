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
import com.example.monitoringapp.databinding.FragmentEditObservationBinding

class EditObservation : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private var speciesNamesList = mutableListOf<String>()
    private val viewModel: PersonalObservationsViewModel by viewModels()
    private lateinit var binding: FragmentEditObservationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        postToList()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEditObservationBinding.inflate(layoutInflater, container, false)

        binding.lifecycleOwner = viewLifecycleOwner

        binding.chooseSpeciesButton.setOnClickListener() {
            Navigation.findNavController(binding.root).navigate(R.id.action_editObservation_to_speciesList)
        }

        binding.deleteButton.setOnLongClickListener {
            val observationId = arguments?.getString("id")
            if (observationId != null) {
                viewModel.deleteObservation(observationId)
            }
            Navigation.findNavController(binding.root).navigate(R.id.action_editObservation_to_personalObservations)
            true
        }


        binding.saveButton.setOnClickListener {
            changeObservation()
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val observationId = arguments?.getString("id")

        if (observationId != null) {
            viewModel.getObservationById(observationId).observe(viewLifecycleOwner) { observation ->
                if (observation != null) {
                    // Bind the observation data to your layout using data binding
                    binding.observation = observation
                }
            }
        }

        val layoutManager = LinearLayoutManager(context)
        recyclerView = view.findViewById(R.id.recycler_view2)
        recyclerView.layoutManager = layoutManager
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = SpeciesListRecyclerAdapter(speciesNamesList)
    }

    private fun changeObservation() {
        val observationId = arguments?.getString("id") ?: return

        val changeObservation = PersonalObservationData(
            id = observationId,
            date = binding.dateField.text.toString(),
            duration = binding.minutesField.text.toString(),
            hour = binding.startTimeField.text.toString(),
            location = binding.chooseLocationButton.text.toString(),
            notes = binding.commentField.text.toString(),
            species = binding.speciesText.text.toString()
        )

        viewModel.changeObservation(changeObservation)
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