package com.example.myprojectteam7

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myprojectteam7.databinding.FragmentCalendarBinding
import com.example.myprojectteam7.viewmodel.CalendarViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
class CalendarFragment : Fragment() {
    lateinit var myCal: Mycalendar
    var binding: FragmentCalendarBinding? = null
    val viewModel = CalendarViewModel("test")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            myCal = it.getSerializable("Calendar") as Mycalendar
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCalendarBinding.inflate(inflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.txtYear?.setText(myCal.year)
        binding?.txtMonth?.setText(myCal.monthStr)
        binding?.recWeek?.layoutManager = GridLayoutManager(context,7)
        binding?.recWeek?.adapter = CalenderAdapter(myCal)

        binding?.txtYear?.setOnClickListener {
            val bundle = bundleOf("Calendar" to myCal)
            findNavController().navigate(R.id.action_calendarFragment_to_yearmonthFragment, bundle)
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}