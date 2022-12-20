package com.example.monitoringapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.EventLogTags
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

//check

class MainActivity : AppCompatActivity() {

    private var titlesList = mutableListOf<String>()
    private var descList = mutableListOf<String>()
    private var imagesList = mutableListOf<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        postToList()

        rv_recyclerView.layoutManager = LinearLayoutManager(this)
        rv_recyclerView.adapter = RecyclerAdapter(titlesList, descList, imagesList)
    }

    private fun addToList(title: String, description: String, image: Int){
        titlesList.add(title)
        descList.add(description)
        imagesList.add(image)
    }

    private fun postToList(){
        for(i in 1..25){
            addToList("Title $i", "Description $i", R.mipmap.ic_launcher_round)
        }
    }
}