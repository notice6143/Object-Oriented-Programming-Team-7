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
import com.example.myprojectteam7.databinding.FragmentCalendarBinding
import com.example.myprojectteam7.viewmodel.CalendarsViewModel

//메인 캘린더
@RequiresApi(Build.VERSION_CODES.O)
class CalendarFragment : Fragment() {
    //lateinit var myCal: Mycalendar
    var binding: FragmentCalendarBinding? = null
    var phone: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            //myCal = it.getSerializable("Calendar") as Mycalendar
            phone = it.getString("Phone") as String
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
        val viewModel = CalendarsViewModel(phone)

        //년도, 월 표시
        viewModel.date.observe(viewLifecycleOwner) {
            binding?.txtYear?.text = viewModel.year.toString()
            binding?.txtMonth?.text = viewModel.monthStr
        }

        //캘린더 리사이클러
        viewModel.calendar.observe(viewLifecycleOwner) {
            binding?.recWeek?.adapter?.notifyDataSetChanged()
            Log.d("캘린더확인",viewModel.calendar.value.toString())
        }
        binding?.recWeek?.layoutManager = GridLayoutManager(context,7)
        binding?.recWeek?.adapter = CalendarAdapter(viewModel.calendar, phone)

        /*
        //날짜 리사이클러
        viewModel.dates.observe(viewLifecycleOwner) {
            binding?.recWeek?.adapter?.notifyDataSetChanged()
        }

        binding?.recWeek?.layoutManager = GridLayoutManager(context,7)
        binding?.recWeek?.adapter = CalenderAdapter(viewModel.dates, phone)*/

        //년월 선택
        binding?.txtYear?.setOnClickListener {
            val bundle = bundleOf("Phone" to phone)
            findNavController().navigate(R.id.action_calendarFragment_to_yearmonthFragment, bundle)
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}