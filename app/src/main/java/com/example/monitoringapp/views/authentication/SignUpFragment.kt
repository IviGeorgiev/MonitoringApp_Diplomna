package com.example.monitoringapp.views.authentication

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import com.example.monitoringapp.R
import com.example.monitoringapp.databinding.FragmentSignUpBinding
import com.google.firebase.ktx.Firebase
import com.google.firebase.auth.ktx.auth

class SignUpFragment : Fragment() {
    private lateinit var binding: FragmentSignUpBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignUpBinding.inflate(layoutInflater, container, false)

        val authentication = Firebase.auth

        binding.redirectLogIn.setOnClickListener {
            Navigation.findNavController(binding.root).navigate(R.id.action_signUpFragment_to_logInFragment)
        }

        binding.btnSignUp.setOnClickListener{
            val email = binding.emailAddress.text.toString()
            val password = binding.password.text.toString()
            val confirmPassword = binding.confirmPassword.text.toString()
            val emailPattern = android.util.Patterns.EMAIL_ADDRESS

            if (email.isBlank()) {
                Toast.makeText(context, "You must enter email", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (!emailPattern.matcher(email).matches()) {
                Toast.makeText(context, "Invalid email format", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (password.isBlank() || confirmPassword.isBlank()) {
                Toast.makeText(context, "You must enter password", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (password.length < 8){
                Toast.makeText(context, "Password must be at least 8 characters long", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (password != confirmPassword) {
                Toast.makeText(context, "Passwords don't match", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            authentication.createUserWithEmailAndPassword(email, password).addOnCompleteListener{
                if (it.isSuccessful) {
                    Toast.makeText(context, "Successfully Signed Up", Toast.LENGTH_SHORT).show()
                    Navigation.findNavController(binding.root).navigate(R.id.action_signUpFragment_to_logInFragment)
                } else {
                    Log.e("SIGN_UP", "Failed to sign up", it.exception)
                    Toast.makeText(context, "Sign Up Failed!", Toast.LENGTH_SHORT).show()
                }
            }
        }

        return binding.root
    }
}