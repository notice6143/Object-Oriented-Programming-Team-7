package com.example.myprojectteam7

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myprojectteam7.databinding.FragmentTodolistBinding
import com.example.myprojectteam7.viewmodel.CalendarsViewModel

@RequiresApi(Build.VERSION_CODES.O)
class TodoListFragment : Fragment() {
    var binding: FragmentTodolistBinding? = null
    val viewModel: CalendarsViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTodolistBinding.inflate(inflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //날짜 표시
        viewModel.user.observe(viewLifecycleOwner) {
            binding?.todolistDate?.text = "${viewModel.monthStr} ${viewModel.day}, ${viewModel.year}"
            binding?.recTodolist?.adapter?.notifyDataSetChanged()
        }

        //Todolist 리사이클러뷰
        viewModel.todolist.observe(viewLifecycleOwner) {
            binding?.recTodolist?.adapter?.notifyDataSetChanged()
        }

        //일정리스트 출력
        binding?.recTodolist?.layoutManager = LinearLayoutManager(context)
        binding?.recTodolist?.adapter = TodoListAdapter(viewModel.todolist, viewModel)


        //일정추가
        binding?.btnEdit?.setOnClickListener {
            findNavController().navigate(R.id.action_todoListFragment_to_todoDetailFragment)
        }


        binding?.btnClose?.setOnClickListener {
            findNavController().navigate(R.id.action_todoListFragment_to_calendarFragment2)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}