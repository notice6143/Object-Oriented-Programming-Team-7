package com.example.myprojectteam7

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myprojectteam7.databinding.ActivityCalendarBinding
import com.example.myprojectteam7.databinding.ActivityMainBinding
import java.time.LocalDate
import java.util.Calendar
import java.util.Date

class CalendarActivity : AppCompatActivity() {      //달력이 구동되는 위치

    lateinit var binding : ActivityCalendarBinding

    var days = mutableListOf<String>("")
    var cal = Calendar.getInstance()
    var year = cal.get(Calendar.YEAR)
    var month = cal.get(Calendar.MONTH)
    var date = cal.get(Calendar.DATE)
    var dayOfWeek = cal.get(Calendar.DAY_OF_WEEK)
    var firstdayOfWeek = (dayOfWeek + 8 - date % 7) % 7


    var nowcal = Mycalender(year,month)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCalendarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recDays.layoutManager = LinearLayoutManager(this)
        binding.recDays.adapter = CalenderAdapter(nowcal)
    }
}