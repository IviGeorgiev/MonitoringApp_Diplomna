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
import com.bumptech.glide.Glide
import com.example.monitoringapp.R
import com.example.monitoringapp.model.ObservationData
import com.example.monitoringapp.databinding.FragmentEditObservationBinding
import com.example.monitoringapp.viewmodel.ObservationsViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import java.io.File
import java.util.*

class EditObservation : Fragment() {
    private val viewModel: ObservationsViewModel by viewModels()
    private lateinit var binding: FragmentEditObservationBinding

    private var selectedImageUri: Uri? = null
    private val user = Firebase.auth.currentUser

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEditObservationBinding.inflate(layoutInflater, container, false)

        binding.lifecycleOwner = viewLifecycleOwner

        binding.updateImagesButton.setOnClickListener{
            if (binding.observation?.creator == user?.email) {
                getContent.launch("image/*")
            } else {
                Toast.makeText(requireContext(), "You can only update images for your own observations", Toast.LENGTH_SHORT).show()
            }
        }

        binding.deleteButton.setOnLongClickListener {
            if (binding.observation?.creator == user?.email) {
                val observationId = arguments?.getString("id")
                if (observationId != null) {
                    viewModel.deleteObservation(observationId)
                }
                Navigation.findNavController(binding.root).navigate(R.id.action_editObservation_to_observationsFragment)
                true
            } else {
                Toast.makeText(requireContext(), "You can delete only your observations", Toast.LENGTH_SHORT).show()
                false
            }
        }

        binding.saveButton.setOnClickListener {
            if (binding.observation?.creator == user?.email) {
                changeFragObservation()
            } else {
                Toast.makeText(requireContext(), "You can't change this observation", Toast.LENGTH_SHORT).show()
            }
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

                    Glide.with(requireContext())
                        .load(observation.photo)
                        .into(binding.imageView)
                }
            }
        }
    }

    private val getContent = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let {
            selectedImageUri = it
            if (selectedImageUri != null) {
                updateImage(selectedImageUri!!)
            }
        }
    }

    private fun updateImage(imageUri: Uri) {
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
                val downloadUri = task.result

                val oldImageUrl = binding.observation?.photo

                val updatedObservation = binding.observation?.copy(photo = downloadUri.toString())
                binding.observation = updatedObservation

                Glide.with(requireContext())
                    .load(downloadUri)
                    .into(binding.imageView)

                // Delete the old image
                if (!oldImageUrl.isNullOrEmpty()) {
                    try {
                        Log.d("UPDATE", "Old image URL: $oldImageUrl")
                        if (oldImageUrl.startsWith("content://")) {
                            val oldImageUri = Uri.parse(oldImageUrl)
                            val oldImagePath = oldImageUri.path
                            if (oldImagePath != null) {
                                val file = File(oldImagePath)
                                if (file.exists()) {
                                    file.delete()
                                    Log.d("UPDATE", "Old image deleted successfully")
                                }
                            }
                        } else {
                            val oldImageRef = Firebase.storage.getReferenceFromUrl(oldImageUrl)
                            oldImageRef.delete().addOnSuccessListener {
                                Log.d("UPDATE", "Old image deleted successfully")
                            }.addOnFailureListener { e ->
                                Log.e("UPDATE", "Failed to delete old image", e)
                            }
                        }
                    } catch (e: IllegalArgumentException) {
                        Log.e("UPDATE", "Invalid old image URL: $oldImageUrl")
                    }
                }

                val observationId = arguments?.getString("id") ?: return@addOnCompleteListener
                val email = user?.email
                val changeObservation = ObservationData(
                    id = observationId,
                    creator = email ?: "",
                    date = binding.dateField.text.toString(),
                    duration = binding.minutesField.text.toString(),
                    hour = binding.startTimeField.text.toString(),
                    location = binding.locationField.text.toString(),
                    photo = downloadUri.toString(),
                    notes = binding.commentField.text.toString(),
                    species = binding.speciesField.text.toString(),
                    speciesDetails = binding.speciesDetailsField.text.toString()
                )
                viewModel.changeObservation(changeObservation)
            } else {
                Log.e("UPDATE", "Failed to update image: ${task.exception}")
                Toast.makeText(requireContext(), "Failed to update image", Toast.LENGTH_SHORT).show()
            }
        }.addOnFailureListener {
            Log.e("UPDATE", "Failed to upload image", it)
            Toast.makeText(requireContext(), "Failed to upload image", Toast.LENGTH_SHORT).show()
        }
    }

    private fun changeFragObservation() {
        val observationId = arguments?.getString("id") ?: return
        val email = user?.email

        val changeObservation = ObservationData(
            id = observationId,
            creator = email ?: "",
            date = binding.dateField.text.toString(),
            duration = binding.minutesField.text.toString(),
            hour = binding.startTimeField.text.toString(),
            location = binding.locationField.text.toString(),
            photo = binding.observation?.photo ?: "",
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