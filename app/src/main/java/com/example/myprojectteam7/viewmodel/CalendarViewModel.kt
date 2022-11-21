package com.example.myprojectteam7.viewmodel

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myprojectteam7.repository.CalendarRepository

const val UNCHECKED_IDX = "2000"
@RequiresApi(Build.VERSION_CODES.O)
class CalendarViewModel(key:String?): ViewModel() {

    private val _idx = MutableLiveData<HashMap<String,Int>>()
    val idx : LiveData<HashMap<String,Int>> get() = _idx

    private val repository = CalendarRepository(key)


    init {
        repository.observeIndex(_idx)
    }

}