package com.example.myprojectteam7

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.myprojectteam7.databinding.FragmentCalendarBinding
import com.example.myprojectteam7.viewmodel.CalendarsViewModel
import java.time.LocalDate

//메인 캘린더
@SuppressLint("NotifyDataSetChanged")
@RequiresApi(Build.VERSION_CODES.O)
class CalendarFragment : Fragment() {
    var binding: FragmentCalendarBinding? = null
    var phone: String = ""
    val viewModel: CalendarsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            phone = it.getString("Phone") as String
            viewModel.setKey(phone)
            viewModel.observeLiveData("date")
            viewModel.observeLiveData("calendar")
            viewModel.observeLiveData("friend")
            viewModel.observeLiveData("user")
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

        //년도, 월 표시, 어답터에 유저정보 전달
        viewModel.user.observe(viewLifecycleOwner) {
            binding?.txtYear?.text = viewModel.year.toString()
            binding?.txtMonth?.text = viewModel.monthStr
            binding?.recWeek?.adapter?.notifyDataSetChanged()
        }

        //캘린더 리사이클러뷰
        viewModel.calendar.observe(viewLifecycleOwner) {
            binding?.recWeek?.adapter?.notifyDataSetChanged()
        }

        //캘린더 출력
        binding?.recWeek?.layoutManager = GridLayoutManager(context,7)
        binding?.recWeek?.adapter = CalendarAdapter(viewModel.calendar, viewModel)

        //이전달로 이동
        binding?.btnBack?.setOnClickListener {
            var tempYear = viewModel.year
            var tempMonth = viewModel.month

            if(tempMonth == 1){
                tempYear--
                tempMonth = 12
            }
            else
                tempMonth--

            val date = LocalDate.of(tempYear, tempMonth, 1)
            viewModel.setViewDate(date)
        }

        //다음달로 이동
        binding?.btnNext?.setOnClickListener {
            var tempYear = viewModel.year
            var tempMonth = viewModel.month

            if(tempMonth == 12){
                tempYear++
                tempMonth = 1
            }
            else
                tempMonth++

            val date = LocalDate.of(tempYear, tempMonth, 1)
            viewModel.setViewDate(date)
        }

        //친구목록 리사이클러뷰
        /*viewModel.friend.observe(viewLifecycleOwner) {
=======

        //캘린더 출력
        binding?.recWeek?.layoutManager = GridLayoutManager(context,7)
        binding?.recWeek?.adapter = CalendarAdapter(viewModel.calendar, viewModel)


        //친구목록 리사이클러뷰
        viewModel.friend.observe(viewLifecycleOwner) {
>>>>>>> origin/master
            binding?.recFriend?.adapter?.notifyDataSetChanged()
        }

        //친구목록 출력
        binding?.recFriend?.layoutManager = GridLayoutManager(context,3)
<<<<<<< HEAD
        binding?.recFriend?.adapter = FriendListAdapter(viewModel.friend, phone)
         */

        binding?.btnFrdlist?.setOnClickListener {
            val bundle = bundleOf("Phone" to phone)
            findNavController().navigate(R.id.action_calendarFragment_to_friendListFragment, bundle)
        }
        //binding?.recFriend?.adapter = FriendListAdapter(viewModel.friend)

        //년월 선택
        binding?.txtYear?.setOnClickListener {
            val bundle = bundleOf("Phone" to phone)
            findNavController().navigate(R.id.action_calendarFragment_to_yearmonthFragment, bundle)
        }

        //세팅
        binding?.btnSetting?.setOnClickListener {
            val bundle = bundleOf("Phone" to phone)
            findNavController().navigate(R.id.action_calendarFragment_to_settingFragment, bundle)
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}