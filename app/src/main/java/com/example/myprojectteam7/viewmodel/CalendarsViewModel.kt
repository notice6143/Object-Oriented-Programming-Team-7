package com.example.myprojectteam7.viewmodel

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myprojectteam7.*
import com.example.myprojectteam7.Todo
import com.example.myprojectteam7.ViewCalendar
import com.example.myprojectteam7.repository.CalendarsRepository
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
data class CalendarsViewModel(val phone:String): ViewModel() {


    //현재 사용자가 선택한 Date -> 년월일 변화에 실시간으로 대응
    private val _date = MutableLiveData<LocalDate>()
    val date : LiveData<LocalDate> get() = _date

    //Calendar(Date, ArrayList<Todolist>) -> 중첩리사이클러뷰
    //CalendarFragment -> CalendarAdapter -> CalendarListAdapter
    //Date -> CalendarAdapter, Todolist -> CalendarListAdapter
    private val _calendar = MutableLiveData<ArrayList<ViewCalendar>>()
    val calendar : LiveData<ArrayList<ViewCalendar>> get() = _calendar

    //사용자가 선택한 일자의 Todolist -> TodoListAdapter -> 리사이클러뷰
    private val _todolist = MutableLiveData<ArrayList<Todo>>()
    val todolist : LiveData<ArrayList<Todo>> get() = _todolist

    //현재 사용자가 선택한 Todo1 -> todolist에서 tododetail
    private val _todo = MutableLiveData<Todo>()
    val todo : LiveData<Todo> get() = _todo

    //친구목록 -> 리사이클러뷰(FriendListAdapter)
    private val _friend = MutableLiveData<ArrayList<Friend>>()
    val friend : LiveData<ArrayList<Friend>> get() = _friend

    //User의 key
    val userkey = "Users/$phone"
    private val repository = CalendarsRepository(userkey)

    init {
        repository.observeDate(_date)   //날짜 포인터
        repository.observeCalendar(_calendar)   //달력 표시
        repository.observeViewTodolist(_todolist) //일정리스트 표시
        repository.observeTodo(_todo) //일정 포인터
        repository.observeFriendlist(_friend) //친구목록 표시
    }

    //get
    //년도, 월, 일, 월 약자
    //date -> _date.value?.toString()
    val year get() = _date.value?.year ?: 0
    val month get() = _date.value?.monthValue ?: 0
    val day get() = _date.value?.dayOfMonth ?: 0
    val monthStr get() = _date.value?.month.toString().substring(0 .. 2)

    //일정 제목, id, date, memo get
    val todouid get() = _todo.value?.uid ?: ""
    val todotitle get() = _todo.value?.title ?: ""
    val tododate get() = _todo.value?.date ?: LocalDate.parse(UNCHECKED_DATE, DateTimeFormatter.ISO_DATE)
    val todomemo get() = _todo.value?.memo ?: ""
    val todokey get() = _todo.value?.key ?: ""
    val todolacation get() = _todo.value?.location ?:""




    //Set
    //viewDate -> 사용자가 가르키고 있는 날짜 set -> 날짜포인터
    private fun modifyViewDate(newDate: LocalDate) {
        repository.postViewDate(newDate)
    }
    fun setViewDate(newDate: LocalDate) {
        modifyViewDate(newDate)
    }

    //UserTodo 일정 한 개 추가
    private fun modifyTodo(newTodo: Todo) {
        repository.postTodo(newTodo)
    }
    fun setTodo(newTodo: Todo) {
        modifyTodo(newTodo)
    }

    //viewTodo -> 사용자가 가르키고 있는 일정 set -> 일정포인터
    private fun modifyViewTodo(newTodo: Todo) {
        repository.postViewTodo(newTodo)
    }
    fun setViewTodo(newTodo: Todo) {
        modifyViewTodo(newTodo)
    }

    //친구추가
    private fun modifyFriend(newFriend: String) {
        repository.postFriend(newFriend)
    }
    fun setFriend(newFriend: String) {
        modifyFriend(newFriend)
    }

}