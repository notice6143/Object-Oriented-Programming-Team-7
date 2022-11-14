package com.example.myprojectteam7

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.myprojectteam7.databinding.ActivitySignupBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase


class SignupActivity : AppCompatActivity() {
    lateinit var binding: ActivitySignupBinding
    lateinit var database: DatabaseReference
    data class User(val username: String? = null, val usernumber: String? = null, val userpassword: String? = null)
    fun writeNewUser(name: String, number: String, password: String) {
        val user = User(name, number, password)
        database.child("Users").child(number).setValue(user)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)
        database = Firebase.database.reference

        binding.btnSignup.setOnClickListener {
            val name: String = binding.edtName.getText().toString()
            val number: String = binding.edtPhone.getText().toString()
            val password: String = binding.edtPw.getText().toString()
            val password2: String = binding.edtPw2.getText().toString()
            database.child("Users").child(number).child("usernumber").get().addOnSuccessListener {
                val key = it.value as String?
                Log.d("값", key ?: "null")
                Log.d("널확인", number)
                if(key != null)
                    binding.txtError.setText("Phone number is invalid or already taken")
                else if(name == "")
                    binding.txtError.setText("Please enter your name")
                else if(number == "")
                    binding.txtError.setText("Please enter your phone number")
                else if(password != password2)
                    binding.txtError.setText("Passwords don't match")
                else if(password == "")
                    binding.txtError.setText("Please enter password")
                else if(key == null) {
                    writeNewUser(name, number, password)
                    val intent = Intent(this, LoginActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    startActivity(intent)
                    finish()
                }
            }
        }
    }
}