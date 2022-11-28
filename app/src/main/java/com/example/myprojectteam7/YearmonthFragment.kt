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
import com.example.myprojectteam7.databinding.FragmentYearmonthBinding
import com.example.myprojectteam7.viewmodel.CalendarsViewModel
import java.time.LocalDate

//년도 월 변경
@RequiresApi(Build.VERSION_CODES.O)
class YearmonthFragment : Fragment() {
    var binding: FragmentYearmonthBinding? = null
    var phone: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            phone = it.getString("Phone") as String
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentYearmonthBinding.inflate(inflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewModel = CalendarsViewModel(phone)
        viewModel.date.observe(viewLifecycleOwner) {

        }
        binding?.btnYmres?.setOnClickListener {
            val year = binding?.edtYear?.getText().toString()
            val month = binding?.edtMonth?.getText().toString()
            Log.d("널확인", year)
            if(year != "" && month != "") {
                val date = LocalDate.of(year.toInt(), month.toInt(), 1)
                viewModel.setViewDate(date)
                val bundle = bundleOf("Phone" to phone)
                findNavController().navigate(
                    R.id.action_yearmonthFragment_to_calendarFragment,
                    bundle
                )
            }
        }

        binding?.btnCancel?.setOnClickListener {
            val bundle = bundleOf("Phone" to phone)
            findNavController().navigate(R.id.action_yearmonthFragment_to_calendarFragment, bundle)
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}