package com.example.myprojectteam7

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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

        binding.btnSignup.setOnClickListener {
            //database = FirebaseDatabase.getInstance().getReference()
            database = Firebase.database.reference
            val username = binding.edtName.getText().toString()
            val usernumber = binding.edtPhone.getText().toString()
            val userpassword = binding.edtPw.getText().toString()
            writeNewUser(username, usernumber, userpassword)
            val intent = Intent(this, LoginActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
            finish()
        }
    }

}