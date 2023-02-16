package com.example.monitoringapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.monitoringapp.data.PersonalObservationData

class PersonalObservationsViewModel: ViewModel() {

    //val myObservations: LiveData<List<PersonalObservationData>> = liveData{emit(listOf(PersonalObservationData("18.01.2023","14:30","Sofia, City-Center", 10)))}
    val myObservations: LiveData<List<PersonalObservationData>> = liveData{
        emit(
            listOf(
                PersonalObservationData("18.01.2023","14:30","Sofia, City-Center", "10"),
                PersonalObservationData("19.02.2023","13:20","Sliven, City-Center", "15"),
                PersonalObservationData("20.02.2023","11:30","Burgas, City-Center", "18"),
                PersonalObservationData("21.02.2023","12:30","Plovdiv, City-Center", "8"),
                PersonalObservationData("22.02.2023","10:30","Sofia, City-Center", "9"),
                PersonalObservationData("23.02.2023","17:30","Sofia, City-Center", "7"),
                PersonalObservationData("24.02.2023","14:00","Varna, City-Center", "17"),
                PersonalObservationData("20.02.2023","11:30","Burgas, City-Center", "18"),
                PersonalObservationData("21.02.2023","12:30","Plovdiv, City-Center", "8")
            )
        )
    }
    //MyObservationData("18.01.2023","14:30","Sofia, City-Center", 10)
}