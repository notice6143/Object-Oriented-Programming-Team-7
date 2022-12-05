package com.example.myprojectteam7

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.fragment.findNavController
import com.example.myprojectteam7.databinding.FragmentSettingBinding
import com.example.myprojectteam7.databinding.FragmentTodoeditBinding
import com.example.myprojectteam7.viewmodel.CalendarsViewModel

@RequiresApi(Build.VERSION_CODES.O)
class SettingFragment : Fragment() {
    var binding: FragmentSettingBinding? = null
    val viewModel: CalendarsViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSettingBinding.inflate(inflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.user.observe(viewLifecycleOwner) {
            binding?.txtNumber?.text = viewModel.phone
        }

        binding?.btnEnter?.setOnClickListener {
            val newFriend : String = binding?.edtFriend?.getText().toString()
            viewModel.setFriend(newFriend)
            Toast.makeText(binding?.root?.context,"이제 $newFriend 님과 일정을 공유할 수 있습니다.", Toast.LENGTH_SHORT).show()
        }

        binding?.btnLogout?.setOnClickListener {
            findNavController().navigate(R.id.action_settingFragment_to_loginFragment)
            Toast.makeText(binding?.root?.context,"로그아웃 성공", Toast.LENGTH_SHORT).show()
        }

        binding?.btnSave?.setOnClickListener {
            findNavController().navigate(R.id.action_settingFragment_to_calendarFragment)
        }

        binding?.btnClose?.setOnClickListener {
            findNavController().navigate(R.id.action_settingFragment_to_calendarFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}