package com.example.myprojectteam7

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myprojectteam7.databinding.ActivityLogin2Binding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {
    lateinit var binding: ActivityLogin2Binding
    lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLogin2Binding.inflate(layoutInflater)
        setContentView(binding.root)
        database = Firebase.database.reference

        binding.btnLogin.setOnClickListener {
            val phone = binding.edtPhone.getText().toString()
            val password= binding.edtPw.getText().toString()
            database.child("Users").get().addOnSuccessListener {
                val userphone = it.child(phone).child("usernumber").value as String?
                val userpassword = it.child(phone).child("userpassword").value as String?
                if(userphone == phone && userpassword == password) {
                    //val intent = Intent(this, CalendarActivity::class.java)
                    val intent = Intent(this, CalendarActivity::class.java)
                    intent.putExtra("phone", phone)
                    intent.putExtra("password", password)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    startActivity(intent)
                    finish()
                }
                else
                    binding.txtError.setText("Incorrect username or password.")
            }
        }
        binding.btnSignup.setOnClickListener {
            val intent = Intent(this, SignupActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
            finish()
        }
    }
}