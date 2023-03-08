package com.example.monitoringapp.data

data class PersonalObservationData(
    val date: String = "",
    val hour: String = "",
    val location: String = "",
    //val photos: List<String> = emptyList(),
    val notes: String = "",
    val duration: String = "",
    val species: String = "")
