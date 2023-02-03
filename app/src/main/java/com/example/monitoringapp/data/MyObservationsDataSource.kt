package com.example.monitoringapp.data

object MyObservationsDataSource {
    fun getObservationData(): ArrayList<MyObservationData> {
        return arrayListOf(
            MyObservationData(
                date = "14.01.2023",
                hour = "12:30",
                location = "Sofia",
                species = 8
            ),
            MyObservationData(
                date = "14.01.2023",
                hour = "12:30",
                location = "Sliven",
                species = 3
            ),
            MyObservationData(
                date = "14.01.2023",
                hour = "12:30",
                location = "Burgas",
                species = 24

            ),
            MyObservationData(
                date = "14.01.2023",
                hour = "12:30",
                location = "Mirkovo",
                species = 6
            )
        )
    }
}