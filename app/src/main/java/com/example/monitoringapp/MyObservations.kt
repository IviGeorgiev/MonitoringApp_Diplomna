package com.example.monitoringapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_my_observations.*

class MyObservations : Fragment() {
    private lateinit var adapter: RecyclerAdapter
    private lateinit var recyclerView: RecyclerView

    private var idList = mutableListOf<String>()
    private var dateList = mutableListOf<String>()
    private var hourList = mutableListOf<String>()
    private var locationList = mutableListOf<String>()
    private var speciesList = mutableListOf<String>()
    //private var imagesList = mutableListOf<Int>()

    private lateinit var fabButton: FloatingActionButton

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_my_observations, container, false)

        fabButton = view.findViewById(R.id.fab_button)
        fabButton.setOnClickListener{
            Navigation.findNavController(view).navigate(R.id.action_myObservations_to_addObservation)
        }

        return view
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        postToList()

        val layoutManager = LinearLayoutManager(context)
        recyclerView = view.findViewById(R.id.recycler_view)
        recyclerView.layoutManager = layoutManager
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = RecyclerAdapter(idList, dateList, hourList, locationList, speciesList)

        //rv_recyclerView.layoutManager = LinearLayoutManager(context)
        //rv_recyclerView.adapter = RecyclerAdapter(idList, dateList, hourList, locationList, speciesList)*/
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