package com.example.monitoringapp

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.*
import com.example.monitoringapp.data.PersonalObservationData
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class PersonalObservationsViewModel(application: Application): AndroidViewModel(application) {

    private val db = FirebaseFirestore.getInstance()
    private val observationsCollection = db.collection("observations")

    private val context = application.applicationContext

    private val _myObservations = MutableLiveData<List<PersonalObservationData>>()
    val myObservations: LiveData<List<PersonalObservationData>>
        get() = _myObservations

    init {
        //Initial loading of data
        observationsCollection.get().addOnSuccessListener { snapshot ->
            val observations = snapshot.documents.mapNotNull { document ->
                document.toObject(PersonalObservationData::class.java)?.copy(id = document.id)
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
                document.toObject(PersonalObservationData::class.java)?.copy(id = document.id)
            } ?: emptyList()
            _myObservations.value = observations
        }
    }

    fun getObservationById(id: String): LiveData<PersonalObservationData?> {
        val observationDocRef = observationsCollection.document(id)

        return liveData {
            val snapshot = observationDocRef.get().await()
            val observation = snapshot.toObject(PersonalObservationData::class.java)
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

    fun changeObservation(observation: PersonalObservationData) {
        observationsCollection.document(observation.id).set(observation)
            .addOnSuccessListener {
                Toast.makeText(context, "Observation is saved", Toast.LENGTH_SHORT).show()
                Log.d("PersonalObservationsVM", "Observation saved successfully")
            }
            .addOnFailureListener { e ->
                Log.e("PersonalObservationsVM", "Error saving observation", e)
            }
    }
}