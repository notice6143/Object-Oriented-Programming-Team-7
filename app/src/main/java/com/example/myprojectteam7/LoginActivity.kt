package com.example.myprojectteam7

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myprojectteam7.databinding.ActivityLogin2Binding

class LoginActivity : AppCompatActivity() {
    lateinit var binding: ActivityLogin2Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLogin2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLogin.setOnClickListener {
            //val intent = Intent(this, CalendarActivity::class.java)
            val intent = Intent(this, TestActivity::class.java)
            val phone = binding.edtPhone.getText().toString()
            val pw= binding.edtPw.getText().toString()
            intent.putExtra("phone", phone)
            intent.putExtra("pw", pw)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
            finish()
        }
        binding.btnSignup.setOnClickListener {
            val intent = Intent(this, SignupActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
            finish()
        }
    }
}