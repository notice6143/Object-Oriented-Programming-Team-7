package com.example.myprojectteam7

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.myprojectteam7.databinding.ActivityMainBinding
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
class MainActivity : AppCompatActivity() {
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