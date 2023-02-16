package com.example.monitoringapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.monitoringapp.databinding.FragmentEditObservationBinding

class EditObservation : Fragment() {

    private lateinit var button: ImageButton
    private lateinit var recyclerView: RecyclerView

    private var speciesNamesList = mutableListOf<String>()

    //private lateinit var binding: FragmentAddObservationBinding
    //private lateinit var observationViewModel: ObservationViewModel
    private val observationViewModel: ObservationViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        postToList()
    }

    //eho

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //val viewModel: ObservationViewModel
        val binding = FragmentEditObservationBinding.inflate(layoutInflater, container, false)
        //binding  = DataBindingUtil.inflate(inflater,R.layout.fragment_add_observation, container, false)

        binding.observation = observationViewModel

        binding.lifecycleOwner = viewLifecycleOwner

        //val observationViewModel = ObservationViewModel()
        //binding.setVariable(FragmentAddObservationBinding.observation, observationViewModel)
        //val view = inflater.inflate(R.layout.fragment_add_observation, container, false)

        //val button = binding.root.findViewById<ImageButton>(R.id.choose_species_button)
        binding.chooseSpeciesButton.setOnClickListener() {
            Navigation.findNavController(binding.root).navigate(R.id.action_editObservation_to_speciesList)
        }

        //val binding = FragmentAddObservationBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val layoutManager = LinearLayoutManager(context)
        recyclerView = view.findViewById(R.id.recycler_view2)
        recyclerView.layoutManager = layoutManager
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = SpeciesListRecyclerAdapter(speciesNamesList)
    }

    private fun addToList(species_names: String){
        speciesNamesList.add(species_names)
    }

    private fun postToList(){
        for(i in 1..9){
            addToList("Species name $i")
        }
    }
    /*override fun onDestroyView() {
        super.onDestroyView()
    }*/
}