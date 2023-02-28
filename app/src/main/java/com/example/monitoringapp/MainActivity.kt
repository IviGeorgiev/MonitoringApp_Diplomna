package com.example.monitoringapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        FirebaseAuth.getInstance().signOut()

        startActivity(Intent(this@MainActivity, RegistrationActivity::class.java))

        /*val authentication = Firebase.auth
        if (authentication.currentUser == null) {
            startActivity(Intent(this@MainActivity, RegistrationActivity::class.java))
        }
        startActivity(Intent(this@MainActivity, UserActivity::class.java))*/


        finish()
    }

}
    /*override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }*/