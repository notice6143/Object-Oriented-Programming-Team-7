package com.example.myprojectteam7

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.example.myprojectteam7.databinding.FragmentTodoeditBinding
import com.example.myprojectteam7.viewmodel.CalendarsViewModel

//Calendar -> todolist -> todoedit
//일정 추가
@RequiresApi(Build.VERSION_CODES.O)
class TodoEditFragment : Fragment() {
    var binding: FragmentTodoeditBinding? = null
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
        binding = FragmentTodoeditBinding.inflate(inflater)
        return binding?.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewModel = CalendarsViewModel()

        viewModel.date.observe(viewLifecycleOwner) {
            binding?.todoeditDate?.text = "${viewModel.monthStr} ${viewModel.day}, ${viewModel.year}"
            binding?.btnSave2?.setOnClickListener {
                val title: String = binding?.edtTitle?.getText().toString()
                val memo: String = binding?.edtMemo?.getText().toString()
                val location: String = binding?.edtLocation?.getText().toString()       //위치 입력받고 지도? 아무튼 구현하기
                if(title != "") {
                    val todo = Todo(phone, title, viewModel.date.value, memo, location)       //key는 왜 없나요
                    viewModel.setTodo(todo)
                    val bundle = bundleOf("Phone" to phone)
                    findNavController().navigate(R.id.action_todoeditFragment_to_todolistFragment, bundle)
                    Toast.makeText(binding?.root?.context,"일정추가 완료", Toast.LENGTH_SHORT).show()
                }
            }
        }

        binding?.btnClose2?.setOnClickListener {
            val bundle = bundleOf("Phone" to phone)
            findNavController().navigate(R.id.action_todoeditFragment_to_todolistFragment,bundle)
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}