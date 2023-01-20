package com.example.monitoringapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.monitoringapp.data.ObservationData

class ObservationViewModel: ViewModel(){
    //val observation1: ObservationData = ObservationData("date","date","date","date","date")
    val observationView: MutableLiveData<ObservationData> = MutableLiveData(ObservationData("18.01.2023","14:30","45","Sofia, City-Center","Windy"))
}