package com.example.monitoringapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.EventLogTags
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.fragment.app.Fragment
import androidx.navigation.ui.setupActionBarWithNavController
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_my_observations.*
import androidx.databinding.DataBindingUtil
//import com.example.monitoringapp.databinding.ActivityMainBinding
import androidx.navigation.ui.setupActionBarWithNavController

//check

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        replaceFragment(MyObservations())

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment

        navController = navHostFragment.navController
    }

    private fun replaceFragment(myObservations: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.nav_host_fragment, myObservations)
        fragmentTransaction.commit()
    }
}