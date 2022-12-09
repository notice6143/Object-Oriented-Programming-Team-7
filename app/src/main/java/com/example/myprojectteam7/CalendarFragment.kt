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
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.myprojectteam7.databinding.FragmentCalendarBinding
import com.example.myprojectteam7.viewmodel.CalendarsViewModel

//메인 캘린더
@RequiresApi(Build.VERSION_CODES.O)
class CalendarFragment : Fragment() {
    var binding: FragmentCalendarBinding? = null
    val viewModel: CalendarsViewModel by activityViewModels()


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
            viewModel.setViewDate(viewModel.date.minusMonths(1))
        }

        //다음달로 이동
        binding?.btnNext?.setOnClickListener {
            viewModel.setViewDate(viewModel.date.plusMonths(1))
        }


        binding?.btnFrdlist?.setOnClickListener {
            findNavController().navigate(R.id.action_calendarFragment_to_friendListFragment)
        }



        //년월 선택
        binding?.txtYear?.setOnClickListener {
            findNavController().navigate(R.id.action_calendarFragment_to_yearmonthFragment)
        }

        //세팅
        binding?.btnSetting?.setOnClickListener {
            findNavController().navigate(R.id.action_calendarFragment_to_settingFragment)
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}