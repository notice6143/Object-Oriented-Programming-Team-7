package com.example.myprojectteam7

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.myprojectteam7.databinding.FragmentSignupBinding
import com.example.myprojectteam7.viewmodel.CalendarsViewModel
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
class SignupFragment : Fragment() {
    var binding: FragmentSignupBinding? = null
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
        binding = FragmentSignupBinding.inflate(inflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.btnSignup?.setOnClickListener {
            val name: String = binding?.edtName?.getText().toString()
            val number: String = binding?.edtPhone?.getText().toString()
            val password: String = binding?.edtPw?.getText().toString()
            val password2: String = binding?.edtPw2?.getText().toString()
            database.child("Users").child(number).child("usernumber").get().addOnSuccessListener {
                val key = it.value as String?
                Log.d("값", key ?: "null")
                Log.d("널확인", number)
                if(key != null)
                    binding?.txtError?.setText("Phone number is invalid or already taken")
                else if(name == "")
                    binding?.txtError?.setText("Please enter your name")
                else if(number == "")
                    binding?.txtError?.setText("Please enter your phone number")
                else if(password != password2)
                    binding?.txtError?.setText("Passwords don't match")
                else if(password == "")
                    binding?.txtError?.setText("Please enter password")
                else if(key == null) {
                    viewModel.setKey(number)
                    viewModel.setNewUser(name, number, password)
                    findNavController().navigate(R.id.action_signupFragment_to_loginFragment)
                    Toast.makeText(binding?.root?.context,"회원가입 성공", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}