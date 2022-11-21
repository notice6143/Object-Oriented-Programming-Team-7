package com.example.myprojectteam7

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.os.bundleOf
import androidx.core.view.children
import androidx.navigation.fragment.findNavController
import com.example.myprojectteam7.databinding.FragmentYearmonthBinding
import com.example.myprojectteam7.viewmodel.CalendarViewModel
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
class YearmonthFragment : Fragment() {
    lateinit var myCal: Mycalendar
    var binding: FragmentYearmonthBinding? = null

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
        binding = FragmentYearmonthBinding.inflate(inflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.btnYmres?.setOnClickListener {
            val year = binding?.edtYear?.getText().toString()
            val month = binding?.edtMonth?.getText().toString()
            val date: LocalDate = LocalDate.of(year.toInt(),month.toInt(),myCal.day.toInt())
            val viewModel = CalendarViewModel((activity as MainActivity).getKey(myCal.id, date))
            viewModel.idx.observe(viewLifecycleOwner) {
                var idx = viewModel.idx.value as HashMap<String,Int>
                val putCal: Mycalendar = Mycalendar(date, myCal.id, idx)
                val bundle = bundleOf("Calendar" to putCal)
                findNavController().navigate(R.id.action_yearmonthFragment_to_calendarFragment, bundle)
            }
        }

        binding?.btnCancel?.setOnClickListener {
            val bundle = bundleOf("Calendar" to myCal)
            findNavController().navigate(R.id.action_yearmonthFragment_to_calendarFragment, bundle)
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}