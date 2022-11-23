package com.example.myprojectteam7.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myprojectteam7.repository.CalendarRepository

class CalendarViewModel: ViewModel() {
    private val _cal = MutableLiveData<String>("")
    val cal : LiveData<String> get() = _cal

    private val repository = CalendarRepository()
}