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
import com.example.myprojectteam7.databinding.FragmentLoginBinding
import com.example.myprojectteam7.viewmodel.CalendarsViewModel
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
class LoginFragment : Fragment() {
    var binding: FragmentLoginBinding? = null
    val viewModel: CalendarsViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding?.btnLogin?.setOnClickListener {
            var now : LocalDate = LocalDate.now()
            var phone = binding?.edtPhone?.getText().toString()
            var password= binding?.edtPw?.getText().toString()


            //로그인 검사
            viewModel.setKey(phone)

            viewModel.user.observe(viewLifecycleOwner) {
                //로그인
                if(viewModel.phone == phone && viewModel.password == password) {
                    viewModel.setViewDate(now)
                    findNavController().navigate(R.id.action_loginFragment_to_calendarFragment)
                    Toast.makeText(binding?.root?.context,"$phone 님 환영합니다.", Toast.LENGTH_SHORT).show()
                }
                else
                    binding?.txtError?.setText("Incorrect username or password.")
            }
        }

        binding?.btnSignup?.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_signupFragment)
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

}