package com.example.myprojectteam7.old

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter

const val UNCHECKED_IDX = "2000"
@RequiresApi(Build.VERSION_CODES.O)
data class CalendarViewModel(val phone:String?, val date: LocalDate): ViewModel() {

    val yym = date.format(DateTimeFormatter.ofPattern("yyyy/MM")).toString()
    //val idxKey = "Users/${phone}/Calendars/${yym}/index"
    val idxKey = "Users/${phone}/View"
    private val _idx = MutableLiveData<HashMap<String,String>>()
    val idx : LiveData<HashMap<String,String>> get() = _idx
    private val repository = CalendarRepository(idxKey)

    private val _view = MutableLiveData<View>()
    val view : LiveData<View> get() = _view
    val viewKey = "Users/${phone}/View"
    private val repoView = CalendarRepository(viewKey)


    init {
        repository.observeIndex(_idx)
        //repoView.observeView(_view)
        //year_repo.observeYear(_year)
    }

    val year: String get() = _idx.value?.get("year") ?: ""
    val month: String get() = _idx.value?.get("month") ?: ""
    val day: String get() = _idx.value?.get("day") ?: ""
    val index: HashMap<String, String> get() {
        var temp = _idx.value?.get("index") ?: ""
        temp = temp.replace("{","")
        temp = temp.replace("}","")
        val map = temp.split(",").associate {
            val (left, right) = it.split("=")
            left to right
        } as HashMap<String,String>
        return map
    }

    /*
    private fun modifyView(year: String, month: String, day: String) {
        repository.postView(year, month, day)
    }

    fun setDateView(date: LocalDate) {
        val year = date.format(DateTimeFormatter.ofPattern("yyyy")).toString()
        val month = date.format(DateTimeFormatter.ofPattern("MM")).toString()
        val day = date.format(DateTimeFormatter.ofPattern("dd")).toString()
        modifyView(year, month, day)
    }

    fun setView(year: String, month: String, day: String) {
        modifyView(year, month, day)
    }
     */



}