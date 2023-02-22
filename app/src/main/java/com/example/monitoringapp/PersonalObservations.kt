package com.example.monitoringapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.example.monitoringapp.databinding.FragmentPersonalObservationsBinding

class PersonalObservations : Fragment() {
    private val viewModel: PersonalObservationsViewModel by viewModels()

    private lateinit var binding: FragmentPersonalObservationsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPersonalObservationsBinding.inflate(layoutInflater, container, false)
        //fabButton = binding.fabButton

        binding.fabButton.setOnClickListener{
            Navigation.findNavController(binding.root).navigate(R.id.action_personalObservations_to_editObservation)
        }

        return binding.root
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val myObservations = viewModel.myObservations

        binding.lifecycleOwner = viewLifecycleOwner

        val recyclerView = binding.personalRecycler
        val adapter = PersonalObservationsRecyclerAdapter(myObservations)
        recyclerView.adapter = adapter
        recyclerView.setHasFixedSize(false)

        myObservations.observe(viewLifecycleOwner) {
            //adapter.notifyItemRangeChanged()
            adapter.notifyDataSetChanged()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }
}