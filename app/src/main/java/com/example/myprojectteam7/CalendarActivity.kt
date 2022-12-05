package com.example.myprojectteam7

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.myprojectteam7.databinding.ActivityMainBinding

class CalendarActivity : AppCompatActivity(){
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navController = binding.frgNav.getFragment<NavHostFragment>().navController
        setupActionBarWithNavController(navController)
        setContentView(binding.root)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = binding.frgNav.getFragment<NavHostFragment>().navController

        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}