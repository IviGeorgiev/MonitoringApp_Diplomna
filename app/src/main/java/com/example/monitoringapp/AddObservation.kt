package com.example.monitoringapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.example.monitoringapp.data.ObservationData
import com.example.monitoringapp.databinding.FragmentAddObservationBinding
import androidx.navigation.fragment.findNavController

class AddObservation : Fragment() {
    private val viewModel: ObservationsViewModel by viewModels()
    private lateinit var binding: FragmentAddObservationBinding

    /*override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }*/

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddObservationBinding.inflate(layoutInflater, container, false)

        binding.lifecycleOwner = viewLifecycleOwner

        binding.submitButton.setOnClickListener {
            addObservation()
        }

        return binding.root
    }

    /*override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }*/

    private fun addObservation(){
        val observation = ObservationData(
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
}