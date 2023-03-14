package com.example.monitoringapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        FirebaseAuth.getInstance().signOut()

        startActivity(Intent(this@MainActivity, RegistrationActivity::class.java))

        finish()
    }

}