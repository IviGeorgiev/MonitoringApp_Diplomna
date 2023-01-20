package com.example.monitoringapp

import androidx.lifecycle.ViewModel
import com.example.monitoringapp.data.MyObservationsData

class MyObservationsViewModel: ViewModel() {

    val myobservation1: MyObservationsData = MyObservationsData(1, "18.01.2023","14:30","Sofia, City-Center", 10)

    /*private var _observation: MutableLiveData<MyObservationsData> = MutableLiveData<MyObservationsData>()
    val currentObservation: LiveData<MyObservationsData>
        get() = _observation

    private var _observationData: ArrayList<MyObservationsData> = ArrayList()
    val observationData: ArrayList<MyObservationsData>
        get() = _observationData

    init {
        _observationData = MyObservationsDataSource.getObservationData()
        _observation.value = _observationData[0]
    }*/
}