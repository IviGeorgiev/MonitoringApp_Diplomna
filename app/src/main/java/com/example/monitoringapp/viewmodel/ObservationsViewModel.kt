package com.example.monitoringapp.viewmodel

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.*
import com.example.monitoringapp.model.ObservationData
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.tasks.await

class ObservationsViewModel(application: Application): AndroidViewModel(application) {

    private val db = FirebaseFirestore.getInstance()
    private val observationsCollection = db.collection("observations")

    private val context = application.applicationContext

    private val _myObservations = MutableLiveData<List<ObservationData>>()
    val myObservations: LiveData<List<ObservationData>>
        get() = _myObservations

    init {
        //Initial loading of data
        observationsCollection.get().addOnSuccessListener { snapshot ->
            val observations = snapshot.documents.mapNotNull { document ->
                document.toObject(ObservationData::class.java)?.copy(id = document.id)
            }
            _myObservations.value = observations
        }.addOnFailureListener { e ->
            Log.e("ObservationsVM", "Error getting documents: ", e)
        }

        //Listen for changes
        observationsCollection.addSnapshotListener { snapshot, e ->
            if (e != null) {
                Log.e("ObservationsVM", "Listen failed.", e)
                return@addSnapshotListener
            }
            val observations = snapshot?.documents?.mapNotNull { document ->
                document.toObject(ObservationData::class.java)?.copy(id = document.id)
            } ?: emptyList()
            _myObservations.value = observations
        }
    }

    fun getObservationById(id: String): LiveData<ObservationData?> {
        val observationDocRef = observationsCollection.document(id)

        return liveData {
            val snapshot = observationDocRef.get().await()
            val observation = snapshot.toObject(ObservationData::class.java)
            emit(observation)
        }
    }

    fun deleteObservation(observationId: String) {
        val observationRef = db.collection("observations").document(observationId)

        observationRef.get().addOnSuccessListener { documentSnapshot ->
            val observation = documentSnapshot.toObject(ObservationData::class.java)
            val photoUrl = observation?.photo

            if (!photoUrl.isNullOrEmpty()) {
                try {
                    val photoRef = Firebase.storage.getReferenceFromUrl(photoUrl)
                    photoRef.delete().addOnSuccessListener {
                        Log.d("ObservationsVM", "Observation photo deleted successfully")
                    }.addOnFailureListener { e ->
                        Log.e("ObservationsVM", "Failed to delete observation photo", e)
                    }
                } catch (e: IllegalArgumentException) {
                    Log.e("ObservationsVM", "Invalid observation photo URL: $photoUrl")
                }
            }

            // Delete the observation
            observationRef.delete().addOnSuccessListener {
                Log.d("ObservationsVM", "Observation deleted successfully")
            }.addOnFailureListener { e ->
                Log.e("ObservationsVM", "Failed to delete observation", e)
            }
        }.addOnFailureListener { e ->
            Log.e("ObservationsVM", "Failed to get observation", e)
        }
    }

    fun changeObservation(observation: ObservationData) {
        observationsCollection.document(observation.id).set(observation)
            .addOnSuccessListener {
                Toast.makeText(context, "Observation is updated", Toast.LENGTH_SHORT).show()
                Log.d("ObservationsVM", "Observation updated successfully")
            }
            .addOnFailureListener { e ->
                Log.e("ObservationsVM", "Error updating observation", e)
            }
    }

    fun addObservation(observation: ObservationData) {
        observationsCollection.add(observation)
            .addOnSuccessListener { documentReference ->
                val observationWithId = observation.copy(id = documentReference.id)
                observationsCollection.document(documentReference.id).set(observationWithId)

                Toast.makeText(context, "Submitted", Toast.LENGTH_SHORT).show()
                Log.d("ObservationsVM", "Observation added successfully")
            }
            .addOnFailureListener { e ->
                Log.e("ObservationsVM", "Error adding observation", e)
            }
    }
}