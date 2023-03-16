package com.example.monitoringapp.views.authentication

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.navigation.Navigation
import com.example.monitoringapp.R
import com.example.monitoringapp.activities.UserActivity
import com.example.monitoringapp.databinding.FragmentLogInBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LogInFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentLogInBinding.inflate(layoutInflater, container, false)
        val authentication = Firebase.auth

        binding.redirectSignUp.setOnClickListener {
            Navigation.findNavController(binding.root).navigate(R.id.action_logInFragment_to_signUpFragment)
        }

        binding.btnLogIn.setOnClickListener{
            val email = binding.loginEmailAddress.text.toString()
            val password = binding.loginPassword.text.toString()
            val emailPattern = android.util.Patterns.EMAIL_ADDRESS

            if (email.isBlank()) {
                Toast.makeText(requireContext(), "You must enter email", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (!emailPattern.matcher(email).matches()) {
                Toast.makeText(requireContext(), "Invalid email format", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (password.isBlank()) {
                Toast.makeText(requireContext(), "You must enter password", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            authentication.signInWithEmailAndPassword(email, password).addOnCompleteListener{
                if (it.isSuccessful) {
                    Toast.makeText(requireContext(), "Successfully Logged in", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(requireActivity(), UserActivity::class.java))
                    requireActivity().finish()
                } else {
                    Log.e("SIGN_UP", "Failed to sign up", it.exception)
                    Toast.makeText(requireContext(), "Wrong email or password!", Toast.LENGTH_SHORT).show()
                }
            }
        }
        return binding.root
    }
}