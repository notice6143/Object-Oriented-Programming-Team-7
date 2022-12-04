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
import com.google.firebase.database.FirebaseDatabase
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
class CalendarsViewModel: ViewModel() {

    //사용자 정보
    private val _user = MutableLiveData<User>()
    val user : LiveData<User> get() = _user

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

    //Users 경로
    private val repository = CalendarsRepository("Users/")


    //LiveData observe
    fun observeLiveData(obs: String) {
        when(obs) {
            "user" -> repository.observeUser(_user) //유저 정보
            "calendar" -> repository.observeCalendar(_calendar)   //달력 표시
            "todolist" -> repository.observeViewTodolist(_todolist) //일정리스트 표시
            "todo" -> repository.observeTodo(_todo) //일정 포인터
            "friend" -> repository.observeFriendlist(_friend) //친구목록 표시
        }
    }

    //viewModel - Repository

    //유저의 키 set
    private fun modifyKey(phone: String) {
        repository.phone = phone
    }

    //새로운 유저 추가
    private fun modifyNewUser(newUser: User) {
        repository.postNewUser(newUser)
    }

    //viewDate -> 사용자가 가르키고 있는 날짜 set -> 날짜포인터
    private fun modifyViewDate(newDate: LocalDate) {
        repository.postViewDate(newDate)
    }

    //UserTodo 일정 한 개 추가
    private fun modifyTodo(newTodo: Todo) {
        repository.postTodo(newTodo)
    }

    //viewTodo -> 사용자가 가르키고 있는 일정 set -> 일정포인터
    private fun modifyViewTodo(newTodo: Todo) {
        repository.postViewTodo(newTodo)
    }

    //친구추가
    private fun modifyFriend(newFriend: String) {
        repository.postFriend(newFriend)
    }


    //get
    //유저정보
    val name get() = _user.value?.username ?: ""
    val phone get() = _user.value?.usernumber ?: ""
    val password get() = _user.value?.userpassword ?: ""

    //년도, 월, 일, 월 약자
    val date get() = _user.value?.viewDate ?: LocalDate.parse(UNCHECKED_DATE, DateTimeFormatter.ISO_DATE)
    val year get() = _user.value?.viewDate?.year ?: 0
    val month get() = _user.value?.viewDate?.monthValue ?: 0
    val day get() = _user.value?.viewDate?.dayOfMonth ?: 0
    val monthStr get() = _user.value?.viewDate?.month.toString().substring(0 .. 2)


    //일정 제목, id, date, memo get
    val todouid get() = _todo.value?.uid ?: ""
    val todoname get() = _todo.value?.author ?: ""
    val todotitle get() = _todo.value?.title ?: ""
    val tododate get() = _todo.value?.date ?: LocalDate.parse(UNCHECKED_DATE, DateTimeFormatter.ISO_DATE)
    val todomemo get() = _todo.value?.memo ?: ""
    val todokey get() = _todo.value?.key ?: ""
    val todolocation get() = _todo.value?.location ?: ""




    //프래그먼트에서 접근
    fun setKey(phone: String) {
        modifyKey(phone)
    }


    fun setNewUser(name: String, number: String, password: String) {
        val now = LocalDate.now()
        val user = User(name, number, password, now, "")
        modifyNewUser(user)
    }


    fun setViewDate(newDate: LocalDate) {
        modifyViewDate(newDate)
    }


    fun setTodo(newTodo: Todo) {
        if(newTodo.title == "")
            newTodo.let {
                it.title = "Title"
            }

        modifyTodo(newTodo)
    }


    fun setViewTodo(newTodo: Todo) {
        modifyViewTodo(newTodo)
    }


    fun setFriend(newFriend: String) {
        modifyFriend(newFriend)
    }

    //유저삭제
    fun deleteUser(){
        repository.userRef.child(phone).removeValue()
    }


    //친구삭제
    fun deleteFriend(friendID: String?) {
        if(friendID != null) {
            repository.userRef.child(phone).child("MyFriendList").child(friendID).removeValue()
            repository.userRef.child(friendID).child("MyFriendList").child(phone).removeValue()
        }
    }


    //일정삭제
    fun deleteTodo(todoKey: String?) {
        if(todoKey != null)
            repository.userRef.child(phone).child("MyTodoList").child(todoKey).removeValue()
    }
}