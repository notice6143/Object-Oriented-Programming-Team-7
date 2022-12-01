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
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.myprojectteam7.databinding.FragmentCalendarBinding
import com.example.myprojectteam7.viewmodel.CalendarsViewModel

//메인 캘린더
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

        //년도, 월 표시
        viewModel.date.observe(viewLifecycleOwner) {
            binding?.txtYear?.text = viewModel.year.toString()
            binding?.txtMonth?.text = viewModel.monthStr
        }

        //캘린더 리사이클러뷰
        viewModel.calendar.observe(viewLifecycleOwner) {
            binding?.recWeek?.adapter?.notifyDataSetChanged()
            Log.d("캘린더확인",viewModel.calendar.value.toString())
        }
        binding?.recWeek?.layoutManager = GridLayoutManager(context,7)
        binding?.recWeek?.adapter = CalendarAdapter(viewModel.calendar, phone)

        //친구 리사이클러뷰
        viewModel.friend.observe(viewLifecycleOwner) {
            binding?.recFriend?.adapter?.notifyDataSetChanged()
        }
        binding?.recFriend?.layoutManager = GridLayoutManager(context,3)
        binding?.recFriend?.adapter = FriendListAdapter(viewModel.friend, phone)

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