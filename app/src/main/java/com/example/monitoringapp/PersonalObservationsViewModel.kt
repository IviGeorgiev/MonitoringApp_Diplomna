package com.example.monitoringapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.monitoringapp.data.MyObservationData

class PersonalObservationsViewModel: ViewModel() {

    val myObservations: LiveData<List<MyObservationData>> = liveData{emit(listOf(MyObservationData("18.01.2023","14:30","Sofia, City-Center", 10)))}

    //MyObservationData("18.01.2023","14:30","Sofia, City-Center", 10)
}