package com.example.monitoringapp.views

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.monitoringapp.activities.RegistrationActivity
import com.example.monitoringapp.databinding.FragmentUserProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import com.google.firebase.auth.ktx.auth

class UserProfile : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentUserProfileBinding.inflate(layoutInflater, container, false)

        val currentUser = FirebaseAuth.getInstance().currentUser

        binding.emailTextView.text = currentUser?.email

        val authentication = Firebase.auth

        binding.btnChangePassword.setOnClickListener{
            val newPassword = binding.changePasswordText.text.toString()
            val confirmNewPassword = binding.confirmChangePasswordText.text.toString()

            if (newPassword.isBlank()|| confirmNewPassword.isBlank()) {
                Toast.makeText(requireContext(), "You must enter password", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (newPassword.length < 8){
                Toast.makeText(requireContext(), "Password must be at least 8 characters long", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (newPassword != confirmNewPassword) {
                Toast.makeText(requireContext(), "Passwords don't match", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            authentication.currentUser?.updatePassword(newPassword)?.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(requireContext(), "Password updated successfully", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(requireActivity(), RegistrationActivity::class.java))
                    requireActivity().finish()
                } else {
                    Toast.makeText(requireContext(), "Password update failed!", Toast.LENGTH_SHORT).show()
                }
            }
        }

        return binding.root
    }
}