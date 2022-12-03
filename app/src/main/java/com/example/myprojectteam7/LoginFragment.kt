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
import com.example.myprojectteam7.databinding.FragmentLoginBinding
import com.example.myprojectteam7.viewmodel.CalendarsViewModel
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
class LoginFragment : Fragment() {
    var binding: FragmentLoginBinding? = null
    lateinit var database: DatabaseReference
    val viewModel: CalendarsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        database = Firebase.database.reference
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
            val now : LocalDate = LocalDate.now()

            val phone = binding?.edtPhone?.getText().toString()
            val password= binding?.edtPw?.getText().toString()
            database.child("Users").get().addOnSuccessListener {
                val userphone = it.child(phone).child("usernumber").value.toString()
                val userpassword = it.child(phone).child("userpassword").value.toString()

                //로그인
                if(userphone == phone && userpassword == password) {
                    viewModel.setKey(phone)
                    viewModel.setViewDate(now)
                    val bundle = bundleOf("Phone" to userphone)
                    findNavController().navigate(R.id.action_loginFragment_to_calendarFragment, bundle)

                    //메시지출력
                    Toast.makeText(binding?.root?.context,"$userphone 님 환영합니다.", Toast.LENGTH_SHORT).show()
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