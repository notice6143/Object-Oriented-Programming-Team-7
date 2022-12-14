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
import com.example.myprojectteam7.databinding.FragmentYearmonthBinding
import com.example.myprojectteam7.viewmodel.CalendarsViewModel
import java.time.LocalDate

//년도 월 변경
@RequiresApi(Build.VERSION_CODES.O)
class YearmonthFragment : Fragment() {
    var binding: FragmentYearmonthBinding? = null
    val viewModel: CalendarsViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentYearmonthBinding.inflate(inflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.btnYmres?.setOnClickListener {
            val year = binding?.edtYear?.getText().toString()
            val month = binding?.edtMonth?.getText().toString()

            //년도와 월 입력 후 변경
            if(year != "" && month != "") {
                val date = LocalDate.of(year.toInt(), month.toInt(), 1)
                viewModel.setViewDate(date)
                findNavController().navigate(R.id.action_yearmonthFragment_to_calendarFragment)
            }
        }

        binding?.btnCancel?.setOnClickListener {
            findNavController().navigate(R.id.action_yearmonthFragment_to_calendarFragment)
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}