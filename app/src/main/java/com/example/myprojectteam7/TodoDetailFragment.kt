package com.example.myprojectteam7

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.myprojectteam7.databinding.FragmentTododetailBinding
import com.example.myprojectteam7.viewmodel.CalendarsViewModel

//일정 상세내용
@RequiresApi(Build.VERSION_CODES.O)
class TodoDetailFragment : Fragment() {
    var binding: FragmentTododetailBinding? = null
    var phone: String = ""
    var todoid: String = ""
    val viewModel: CalendarsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            phone = it.getString("Phone") as String
            todoid = it.getString("TodoID") as String
            viewModel.setKey(todoid)
            viewModel.observeLiveData("todo")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTododetailBinding.inflate(inflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //유저가 선택한 일정을 표시
        viewModel.todo.observe(viewLifecycleOwner) {
            binding?.txtTitle?.text = viewModel.todotitle
            binding?.txtDate?.text = viewModel.tododate.toString()
            binding?.txtUid?.text = viewModel.todouid
            binding?.edtMemo?.hint = viewModel.todomemo

            binding?.btnEdit?.setOnClickListener {
                val memo: String = binding?.edtMemo?.getText().toString()
                val todo = Todo(viewModel.todouid,viewModel.todoname ,viewModel.todotitle, viewModel.tododate, memo, viewModel.todokey)
                viewModel.setTodo(todo)
                val bundle = bundleOf("Phone" to phone)
                findNavController().navigate(R.id.action_tododetailFragment_to_todolistFragment, bundle)
                Toast.makeText(binding?.root?.context,"일정이 수정되었습니다.", Toast.LENGTH_SHORT).show()
            }
        }

        binding?.btnClose?.setOnClickListener {
            val bundle = bundleOf("Phone" to phone)
            findNavController().navigate(R.id.action_tododetailFragment_to_todolistFragment,bundle)
        }

    }
    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}