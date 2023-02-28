package com.example.monitoringapp

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.monitoringapp.databinding.FragmentLogInBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LogInFragment : Fragment() {
    private lateinit var binding: FragmentLogInBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLogInBinding.inflate(layoutInflater, container, false)

        val authentication = Firebase.auth

        binding.btnLogIn.setOnClickListener{
            val email = binding.loginEmailAddress.text.toString()
            val password = binding.loginPassword.text.toString()

            if (email.isBlank()) {
                Toast.makeText(context, "You must enter email", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (password.isBlank()) {
                Toast.makeText(context, "You must enter password", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            authentication.signInWithEmailAndPassword(email, password).addOnCompleteListener{
                if (it.isSuccessful) {
                    Toast.makeText(context, "Successfully Logged in", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(requireActivity(), UserActivity::class.java))
                    requireActivity().finish()
                } else {
                    //Log.e("SIGN_UP", "Failed to sign up", it.exception)
                    Toast.makeText(context, "Log In Failed!", Toast.LENGTH_SHORT).show()
                }
            }
        }
        return binding.root
    }
}