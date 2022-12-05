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
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.myprojectteam7.bin.Todo
import com.example.myprojectteam7.databinding.FragmentTodoeditBinding
import com.example.myprojectteam7.viewmodel.CalendarsViewModel

//Calendar -> todolist -> todoedit
//일정 추가
@RequiresApi(Build.VERSION_CODES.O)
class TodoEditFragment : Fragment() {
    var binding: FragmentTodoeditBinding? = null
    val viewModel: CalendarsViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTodoeditBinding.inflate(inflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val todo = Todo()

        viewModel.user.observe(viewLifecycleOwner) {
            binding?.todoeditDate?.text = "${viewModel.monthStr} ${viewModel.day}, ${viewModel.year}"
            //일정 기본값
            todo.let {
                it.uid = viewModel.phone
                it.author = viewModel.name
                it.date = viewModel.date
            }
        }

        binding?.btnSave2?.setOnClickListener {
            val title: String = binding?.edtTitle?.getText().toString()
            val memo: String = binding?.edtMemo?.getText().toString()
            val location: String = binding?.edtLocation?.getText().toString()
            //일정 편집
            todo.let {
                it.title = title
                it.memo = memo
                it.location = location
            }

            viewModel.setTodo(todo)
            findNavController().navigate(R.id.action_todoEditFragment_to_todoListFragment)
            Toast.makeText(binding?.root?.context,"일정추가 완료", Toast.LENGTH_SHORT).show()
        }

        binding?.btnClose2?.setOnClickListener {
            findNavController().navigate(R.id.action_todoEditFragment_to_todoListFragment)
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}