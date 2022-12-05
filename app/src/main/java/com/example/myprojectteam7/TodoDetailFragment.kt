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
import com.example.myprojectteam7.databinding.FragmentTododetailBinding
import com.example.myprojectteam7.viewmodel.CalendarsViewModel

//일정 상세내용
@RequiresApi(Build.VERSION_CODES.O)
class TodoDetailFragment : Fragment() {
    var binding: FragmentTododetailBinding? = null
    val viewModel: CalendarsViewModel by activityViewModels()


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
            binding?.txtLocation?.text = viewModel.todolocation

            binding?.btnEdit?.setOnClickListener {
                val memo: String = binding?.edtMemo?.getText().toString()
                val todo = viewModel.todo.value ?: Todo()
                todo?.let {
                    it.memo = memo
                }

                viewModel.setTodo(todo)
                findNavController().navigate(R.id.action_tododetailFragment_to_todolistFragment)
                Toast.makeText(binding?.root?.context,"일정이 수정되었습니다.", Toast.LENGTH_SHORT).show()
            }

            //location 입력 여부에 따라 창 띄워주기
            binding?.txtLocation?.setOnClickListener {
                if(viewModel.todolocation != ""){
                    findNavController().navigate(R.id.action_tododetailFragment_to_mapFragment2)        //이거 타고 지도 띄워주기
                }
            }
            binding?.btnClose?.setOnClickListener {
                findNavController().navigate(R.id.action_tododetailFragment_to_todolistFragment)
            }
        }

    }
    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}