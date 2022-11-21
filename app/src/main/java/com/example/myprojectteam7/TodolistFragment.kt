package com.example.myprojectteam7

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myprojectteam7.databinding.FragmentTodolistBinding

@RequiresApi(Build.VERSION_CODES.O)
class TodolistFragment : Fragment() {
    lateinit var myCal: Mycalendar
    var binding: FragmentTodolistBinding? = null

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
        binding = FragmentTodolistBinding.inflate(inflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.scheduleDate?.text =  myCal.monthStr + " " + myCal.day + ", " + myCal.year
        binding?.recSchedule?.layoutManager = LinearLayoutManager(context)
        binding?.recSchedule?.adapter = TodolistAdapter(myCal)

        binding?.btnEdit?.setOnClickListener {
            val bundle = bundleOf("Calendar" to myCal)
            findNavController().navigate(R.id.action_todolistFragment_to_todoeditFragment,bundle)
        }

        binding?.btnClose?.setOnClickListener {
            val bundle = bundleOf("Calendar" to myCal)
            findNavController().navigate(R.id.action_todolistFragment_to_calendarFragment,bundle)
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}