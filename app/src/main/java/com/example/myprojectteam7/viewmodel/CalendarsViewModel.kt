package com.example.myprojectteam7.viewmodel

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myprojectteam7.*
import com.example.myprojectteam7.Date
import com.example.myprojectteam7.Todo
import com.example.myprojectteam7.ViewCalendar
import com.example.myprojectteam7.repository.CalendarsRepository
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
data class CalendarsViewModel(val phone:String): ViewModel() {

    //현재 사용자가 가르키고 있는 Date를 기준으로 한달을 가져옴 -> 리사이클러뷰
    private val _dates = MutableLiveData<ArrayList<Date>>()
    val dates : LiveData<ArrayList<Date>> get() = _dates

    //현재 사용자가 선택한 Date -> 년월일 변화에 실시간으로 대응
    private val _date = MutableLiveData<LocalDate>()
    val date : LiveData<LocalDate> get() = _date

    //현재 사용자가 선택한 Todo1 -> todolist에서 tododetail
    private val _todo = MutableLiveData<Todo>()
    val todo : LiveData<Todo> get() = _todo

    //사용자가 선택한 일자의 Todolist -> todolist에서 표시
    private val _todolist = MutableLiveData<ArrayList<Todo>>()
    val todolist : LiveData<ArrayList<Todo>> get() = _todolist

    //Date-Todo해쉬맵 구조
    private val _dateTodo = MutableLiveData<HashMap<Date,ArrayList<Todo>>>()
    val dateTodo : LiveData<HashMap<Date,ArrayList<Todo>>> get() = _dateTodo

    //Calendar(Date, Array<Todo>)
    private val _calendar = MutableLiveData<ArrayList<ViewCalendar>>()
    val calendar : LiveData<ArrayList<ViewCalendar>> get() = _calendar

    //User의 key
    val keyDate = "Users/$phone"
    private val repository = CalendarsRepository(keyDate)

    init {
        //repository.observeViewDate(_dates)
        repository.observeDate(_date)
        //repository.observeViewTodolist(_todolist)
        //repository.observeMapDateTodo(_dateTodo)
        repository.observeCalendar(_calendar)
    }

    //년도, 월, 일, 월 약자 get
    //date -> _now.value?.toString()
    val year get() = _date.value?.year ?: 0
    val month get() = _date.value?.monthValue ?: 0
    val day get() = _date.value?.dayOfMonth ?: 0
    //val date get() = _now.value?.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")).toString()
    val monthStr get() = _date.value?.month.toString().substring(0 .. 2)
    val uid get() = phone

    /*val year get() = _dates.value?.get(10)?.date1?.year ?: 0
    val month get() = _dates.value?.get(10)?.date1?.monthValue ?: 0
    val monthStr get() = _dates.value?.get(10)?.date1?.month.toString().substring(0 .. 2)*/



    //viewDate -> 사용자가 가르키고 있는 날짜 set
    private fun modifyViewDate(newDate: LocalDate) {
        repository.postDate(newDate)
    }
    fun setViewDate(newDate: LocalDate) {
        modifyViewDate(newDate)
    }

    //UserTodo set
    private fun modifyTodo(newTodo: Todo) {
        repository.postTodo(newTodo)
    }
    fun setTodo(newTodo: Todo) {
        modifyTodo(newTodo)
    }

    fun viewCalendar(date: LocalDate) {
        //val firstday = LocalDate.of(2022,11,1)
        val firstday = LocalDate.of(date.year,date.monthValue,1)
        val firstdayOfWeek = if(firstday.dayOfWeek.value!=7) firstday.dayOfWeek.value else 0
        val date = firstday.minusDays(firstdayOfWeek.toLong())
        val Calendars = ArrayList<Date>()
        for(i in 0.. 41) {
            val temp = date.plusDays(i.toLong())
            Calendars.add(Date(temp))
        }
        _dates.value = Calendars
    }
}