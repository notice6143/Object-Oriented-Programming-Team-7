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
import com.example.myprojectteam7.databinding.FragmentTodolistBinding
import com.example.myprojectteam7.viewmodel.CalendarsViewModel

@RequiresApi(Build.VERSION_CODES.O)
class TodoListFragment : Fragment() {
    var binding: FragmentTodolistBinding? = null
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
        binding = FragmentTodolistBinding.inflate(inflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewModel = CalendarsViewModel(phone)
        viewModel.date.observe(viewLifecycleOwner) {
            binding?.todolistDate?.text = "${viewModel.monthStr} ${viewModel.day}, ${viewModel.year}"
        }

        //Todolist 리사이클러뷰
        viewModel.todolist.observe(viewLifecycleOwner) {
            binding?.recTodolist?.adapter?.notifyDataSetChanged()
        }

        binding?.recTodolist?.layoutManager = LinearLayoutManager(context)
        binding?.recTodolist?.adapter = TodoListAdapter(viewModel.todolist, phone)

        binding?.btnEdit?.setOnClickListener {
            val bundle = bundleOf("Phone" to phone)
            findNavController().navigate(R.id.action_todolistFragment_to_todoeditFragment,bundle)
        }

        binding?.btnClose?.setOnClickListener {
            val bundle = bundleOf("Phone" to phone)
            findNavController().navigate(R.id.action_todolistFragment_to_calendarFragment,bundle)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}