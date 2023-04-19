package com.example.monitoringapp.views

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.example.monitoringapp.R
import com.example.monitoringapp.adapter.ObservationsRecyclerAdapter
import com.example.monitoringapp.model.ObservationData
import com.example.monitoringapp.databinding.FragmentObservationsBinding
import com.example.monitoringapp.viewmodel.ObservationsViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class ObservationsFragment : Fragment() {

    private val viewModel: ObservationsViewModel by viewModels()
    private lateinit var binding: FragmentObservationsBinding
    private lateinit var adapter: ObservationsRecyclerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentObservationsBinding.inflate(layoutInflater, container, false)

        //Filter the users observations
        binding.myFilterFabButton.setOnClickListener{
            val currentUser = FirebaseAuth.getInstance().currentUser
            val userEmail = currentUser?.email

            if (userEmail != null) {
                val query = FirebaseFirestore.getInstance().collection("observations")
                    .whereEqualTo("creator", userEmail)

                query.get().addOnSuccessListener { querySnapshot ->
                    val filteredObservations = querySnapshot.toObjects(ObservationData::class.java)
                    Log.d("ObservationsFragment", "Filtered observations: $filteredObservations")
                    adapter.updateObservations(filteredObservations)
                    adapter.notifyDataSetChanged()
                    Toast.makeText(requireContext(), "My observations", Toast.LENGTH_SHORT).show()
                }.addOnFailureListener { exception ->
                    Log.e("ObservationsFragment", "Error filtering observations", exception)
                }
            }
        }

        //Sort by species count
        binding.filterFabButton.setOnClickListener{
            val query = FirebaseFirestore.getInstance().collection("observations")
                .orderBy("species", Query.Direction.DESCENDING)

            query.get().addOnSuccessListener { querySnapshot ->
                val sortedObservations = querySnapshot.toObjects(ObservationData::class.java)
                    .sortedByDescending { it.species.toIntOrNull() }
                Log.d("ObservationsFragment", "Sorted observations: $sortedObservations")
                adapter.updateObservations(sortedObservations)
                adapter.notifyDataSetChanged()
            }.addOnFailureListener { exception ->
                Log.e("ObservationsFragment", "Error getting observations", exception)
            }
        }

        binding.addFabButton.setOnClickListener{
            Navigation.findNavController(binding.root).navigate(R.id.action_observationsFragment_to_addObservation)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val myObservations = viewModel.myObservations

        binding.lifecycleOwner = viewLifecycleOwner

        val recyclerView = binding.observationRecycler
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

}