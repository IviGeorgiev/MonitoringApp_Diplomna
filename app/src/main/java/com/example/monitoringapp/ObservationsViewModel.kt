package com.example.monitoringapp

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.*
import com.example.monitoringapp.data.ObservationData
import com.google.firebase.firestore.FirebaseFirestore
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
            Log.e("PersonalObservationsVM", "Error getting documents: ", e)
        }

        //Listen for changes
        observationsCollection.addSnapshotListener { snapshot, e ->
            if (e != null) {
                Log.e("PersonalObservationsVM", "Listen failed.", e)
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
        observationsCollection.document(observationId).delete()
            .addOnSuccessListener {
                Toast.makeText(context, "Observation was deleted", Toast.LENGTH_SHORT).show()
                Log.d("PersonalObservationsVM", "Observation deleted successfully")
            }
            .addOnFailureListener { e ->
                Log.e("PersonalObservationsVM", "Error deleting observation", e)
            }
    }

    fun changeObservation(observation: ObservationData) {
        observationsCollection.document(observation.id).set(observation)
            .addOnSuccessListener {
                Toast.makeText(context, "Observation is saved", Toast.LENGTH_SHORT).show()
                Log.d("PersonalObservationsVM", "Observation saved successfully")
            }
            .addOnFailureListener { e ->
                Log.e("PersonalObservationsVM", "Error saving observation", e)
            }
    }

    fun addObservation(observation: ObservationData) {
        observationsCollection.add(observation)
            .addOnSuccessListener {
                Toast.makeText(context, "Submitted", Toast.LENGTH_SHORT).show()
                Log.d("PersonalObservationsVM", "Observation added successfully")
            }
            .addOnFailureListener { e ->
                Log.e("PersonalObservationsVM", "Error adding observation", e)
            }
    }
}