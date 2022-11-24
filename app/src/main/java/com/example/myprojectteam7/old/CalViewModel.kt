package com.example.myprojectteam7.old

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myprojectteam7.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
data class CalViewModel(val phone:String?): ViewModel() {

    private val _view = MutableLiveData<View>()
    val view : LiveData<View> get() = _view
    val viewKey = "Users/${phone}"
    private val repoView = CalendarRepository(viewKey)


    init {
        repoView.observeView(_view)
    }

    val id get() = phone ?: ""
    val year get()= _view.value?.year ?: ""
    val month get()= _view.value?.month ?: ""
    val day get()= _view.value?.day ?: ""
    val index get()= _view.value?.index as HashMap<String, String>
    val date: LocalDate get() {
        return LocalDate.of(year.toInt(),month.toInt(),day.toInt())
    }
    val LiveCal : Mycalendar get() = Mycalendar(date, id, index)

    private fun modifyView(newView: View) {
        repoView.postView(newView)
    }


    fun setDateView(date: LocalDate, index: HashMap<String, String>) {
        val year = date.format(DateTimeFormatter.ofPattern("yyyy")).toString()
        val month = date.format(DateTimeFormatter.ofPattern("MM")).toString()
        val day = date.format(DateTimeFormatter.ofPattern("dd")).toString()
        val newView = View(year,month,day,index)
        modifyView(newView)
    }

    fun setView(year: String, month: String, day: String, index: HashMap<String, String>) {
        val newView = View(year,month,day,index)
        modifyView(newView)
    }
}