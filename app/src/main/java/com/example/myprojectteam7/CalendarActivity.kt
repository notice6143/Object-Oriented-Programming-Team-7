package com.example.myprojectteam7

import android.annotation.SuppressLint
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.myprojectteam7.databinding.ActivityCalendarBinding
import com.example.myprojectteam7.databinding.ActivityMainBinding
import com.example.myprojectteam7.viewmodel.CalendarsViewModel

class CalendarActivity : AppCompatActivity(){
    lateinit var binding: ActivityCalendarBinding
    private val viewModel: CalendarsViewModel by viewModels()

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val intent = getIntent()


        binding = ActivityCalendarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navController = binding.frgCal.getFragment<NavHostFragment>().navController
        setupActionBarWithNavController(navController)
        setContentView(binding.root)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = binding.frgCal.getFragment<NavHostFragment>().navController

        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}