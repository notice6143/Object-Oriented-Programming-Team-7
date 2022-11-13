package com.example.myprojectteam7

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.myprojectteam7.databinding.ActivitySignupBinding
import com.example.myprojectteam7.databinding.ActivityTestBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.snapshots
import com.google.firebase.ktx.Firebase

class TestActivity : AppCompatActivity() {
    lateinit var binding: ActivityTestBinding
    lateinit var database: DatabaseReference

    data class Calendar(val title: String? = null, val date: String? = null, val memo: String? = null)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTestBinding.inflate(layoutInflater)
        setContentView(binding.root)
        database = Firebase.database.reference

        var d = ArrayList<String>()
        val a = database.child("Users").child("010-1234-5678").child("username").get().addOnSuccessListener {
            val c = it.value as String
            d.add(c)
            Log.d("데이터베이스", c.toString())
            Log.d("데이터2", d.toString())
        }
        val phone=intent.getStringExtra("phone")
        val pw = intent.getStringExtra("pw")
        binding.txtTest1.setText(phone)
        binding.txtTest2.setText(pw)
        binding.btnTest1.setOnClickListener {
            database.child("Users").child("010-1234-5678").child("Calendars").child("aaaa").get().addOnSuccessListener {
                val title = it.child("title").value as String
                val date = it.child("date").value as String
                val memo = it.child("memo").value as String
                val cal = Calendar(title, date, memo)
                binding.txtTest2.setText(title)
                binding.txtTest3.setText(date)
                binding.txtTest4.setText(memo)
            }
        }
        binding.btnTest2.setOnClickListener() {
            val key = binding.edtTest1.getText().toString()
            val title = binding.edtTest2.getText().toString()
            val date = binding.edtTest3.getText().toString()
            val memo = binding.edtTest4.getText().toString()
            val cal = Calendar(title, date, memo)
            database.child("Users").child("010-1234-5678").child("Calendars").child(key).setValue(cal)
        }
    }
}