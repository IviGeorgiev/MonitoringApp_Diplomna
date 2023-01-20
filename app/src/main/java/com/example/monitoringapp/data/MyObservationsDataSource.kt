package com.example.monitoringapp.data

import com.example.monitoringapp.data.MyObservationsData

object MyObservationsDataSource {
    fun getObservationData(): ArrayList<MyObservationsData> {
        return arrayListOf(
            MyObservationsData(
                id = 1,
                date = "14.01.2023",
                hour = "12:30",
                location = "Sofia",
                species = 8
            ),
            MyObservationsData(
                id = 2,
                date = "14.01.2023",
                hour = "12:30",
                location = "Sliven",
                species = 3
            ),
            MyObservationsData(
                id = 3,
                date = "14.01.2023",
                hour = "12:30",
                location = "Burgas",
                species = 24

            ),
            MyObservationsData(
                id = 4,
                date = "14.01.2023",
                hour = "12:30",
                location = "Mirkovo",
                species = 6
            )
        )
    }
}