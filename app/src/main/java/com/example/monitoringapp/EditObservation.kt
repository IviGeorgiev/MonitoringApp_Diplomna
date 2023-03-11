package com.example.monitoringapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.example.monitoringapp.data.ObservationData
import com.example.monitoringapp.databinding.FragmentEditObservationBinding

class EditObservation : Fragment() {
    private val viewModel: ObservationsViewModel by viewModels()
    private lateinit var binding: FragmentEditObservationBinding

    /*override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //postToList()
    }*/

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEditObservationBinding.inflate(layoutInflater, container, false)

        binding.lifecycleOwner = viewLifecycleOwner

        binding.deleteButton.setOnLongClickListener {
            val observationId = arguments?.getString("id")
            if (observationId != null) {
                viewModel.deleteObservation(observationId)
            }
            Navigation.findNavController(binding.root).navigate(R.id.action_editObservation_to_observationsFragment)
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
                    binding.observation = observation
                }
            }
        }
    }

    private fun changeObservation() {
        val observationId = arguments?.getString("id") ?: return

        val changeObservation = ObservationData(
            id = observationId,
            date = binding.dateField.text.toString(),
            duration = binding.minutesField.text.toString(),
            hour = binding.startTimeField.text.toString(),
            location = binding.locationField.text.toString(),
            notes = binding.commentField.text.toString(),
            species = binding.speciesField.text.toString(),
            speciesDetails = binding.speciesDetailsField.text.toString()
        )

        if (changeObservation.date.isEmpty() ||
            changeObservation.hour.isEmpty() ||
            changeObservation.location.isEmpty() ||
            changeObservation.duration.isEmpty() ||
            changeObservation.species.isEmpty() ||
            changeObservation.speciesDetails.isEmpty()) {
            Toast.makeText(context, "You can't have empty fields", Toast.LENGTH_SHORT).show()
            return
        }else{
            viewModel.changeObservation(changeObservation)
        }
    }
}