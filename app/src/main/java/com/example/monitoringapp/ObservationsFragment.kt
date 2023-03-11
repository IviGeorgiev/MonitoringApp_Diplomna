package com.example.monitoringapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.example.monitoringapp.databinding.FragmentObservationsBinding

class ObservationsFragment : Fragment() {
    private val viewModel: ObservationsViewModel by viewModels()

    private lateinit var binding: FragmentObservationsBinding
    private lateinit var adapter: ObservationsRecyclerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentObservationsBinding.inflate(layoutInflater, container, false)

        binding.fabButton.setOnClickListener{
            Navigation.findNavController(binding.root).navigate(R.id.action_observationsFragment_to_addObservation)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val myObservations = viewModel.myObservations

        binding.lifecycleOwner = viewLifecycleOwner

        val recyclerView = binding.personalRecycler
        adapter = ObservationsRecyclerAdapter(emptyList())

        recyclerView.adapter = adapter
        recyclerView.setHasFixedSize(false)

        adapter.setOnItemClickListener { observation ->
            val bundle = bundleOf("id" to observation.id)
            Navigation.findNavController(binding.root).navigate(R.id.action_observationsFragment_to_editObservation, bundle)
        }

        myObservations.observe(viewLifecycleOwner) { observations  ->
            adapter.updateObservations(observations)
        }

    }

    /*override fun onDestroyView() {
        super.onDestroyView()
    }*/
}