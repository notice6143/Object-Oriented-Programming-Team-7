package com.example.myprojectteam7.repository

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

@RequiresApi(Build.VERSION_CODES.O)
class CalendarRepository(key: String?) {
    val database = Firebase.database
    val userRef = database.getReference(key ?: "bug")

    fun observeCal(cal: MutableLiveData<String>) {
        userRef.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                cal.postValue(snapshot.value.toString())
            }
            override fun onCancelled(error: DatabaseError) {
            }
        })
    }
    fun postCal(newValue: String) {
        userRef.setValue(newValue)
    }

    fun observeIndex(idx: MutableLiveData<HashMap<String,Int>>) {
        userRef.addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                var temp = HashMap<String,Int>()
                for(i in snapshot.children) {
                    Log.d("스냅샷1",i.toString())
                    Log.d("스냅샷2",i.key.toString())
                    Log.d("스냅샷3",i.value.toString())
                    temp.put(i.key.toString(),i.value.toString().toInt())
                }
                idx.postValue(temp)
            }
            override fun onCancelled(error: DatabaseError) {
                var temp = HashMap<String,Int>()
                temp.put("none", 0)
                idx.postValue(temp)
            }
        })
    }
    fun postIndex(newValue: String) {
        userRef.setValue(newValue)
    }
}