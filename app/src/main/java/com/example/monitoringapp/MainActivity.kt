package com.example.monitoringapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.EventLogTags
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

//check

class MainActivity : AppCompatActivity() {

    private var idList = mutableListOf<String>()
    private var dateList = mutableListOf<String>()
    private var hourList = mutableListOf<String>()
    private var locationList = mutableListOf<String>()
    private var speciesList = mutableListOf<String>()
    //private var imagesList = mutableListOf<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        postToList()

        rv_recyclerView.layoutManager = LinearLayoutManager(this)
        rv_recyclerView.adapter = RecyclerAdapter(idList, dateList, hourList, locationList, speciesList)
    }

    private fun addToList(list_id: String, date: String, hour: String, location: String, species: String, image: Int){
        idList.add(list_id)
        dateList.add(date)
        hourList.add(hour)
        locationList.add(location)
        speciesList.add(species)
        //imagesList.add(image)
    }

    private fun postToList(){
        for(i in 1..25){
            addToList("#$i", "Date $i","Hour $i", "Location $i", "/ Species $i", R.mipmap.ic_launcher_round)
        }
    }
}