package com.example.monitoringapp.views

import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.example.monitoringapp.model.ObservationData
import com.example.monitoringapp.databinding.FragmentAddObservationBinding
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.monitoringapp.R
import com.example.monitoringapp.viewmodel.ObservationsViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.util.*
import com.google.firebase.storage.ktx.storage

class AddObservation : Fragment() {

    private val viewModel: ObservationsViewModel by viewModels()
    private lateinit var binding: FragmentAddObservationBinding

    private val user = Firebase.auth.currentUser
    private var selectedImageUri: Uri? = null
    private var downloadUri: Uri? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddObservationBinding.inflate(layoutInflater, container, false)

        binding.lifecycleOwner = viewLifecycleOwner

        binding.uploadImagesButton.setOnClickListener{
            getContent.launch("image/*")
        }

        binding.seeLocationButton.setOnClickListener{
            Navigation.findNavController(binding.root).navigate(R.id.action_addObservation_to_mapFragment)
        }

        binding.submitButton.setOnClickListener {
            addFragObservation()
        }

        return binding.root
    }

    private val getContent = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let {
            selectedImageUri = it
            uploadImage(selectedImageUri!!)
        }
    }

    private fun uploadImage(imageUri: Uri) {
        val imageName = UUID.randomUUID().toString()
        val storageRef = Firebase.storage.reference.child("photos/$imageName")

        val uploadTask = storageRef.putFile(imageUri)
        uploadTask.continueWithTask { task ->
            if (!task.isSuccessful) {
                task.exception?.let { throw it }
            }
            storageRef.downloadUrl
        }.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                downloadUri = task.result
                Glide.with(requireContext())
                    .load(downloadUri.toString())
                    .into(binding.imageView)
            } else {
                Log.e("UPLOAD", "Failed to upload image: ${task.exception}")
                Toast.makeText(requireContext(), "Failed to upload image", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun addFragObservation(){
        val email = user?.email
        val observation = ObservationData(
            id = "",
            creator = email ?: "",
            date = binding.dateField.text.toString(),
            hour = binding.startTimeField.text.toString(),
            location = binding.locationField.text.toString(),
            notes = binding.commentField.text.toString(),
            photo = downloadUri.toString(),
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
            Toast.makeText(context, "Submitted", Toast.LENGTH_SHORT).show()
            findNavController().navigateUp()
        }
    }
}