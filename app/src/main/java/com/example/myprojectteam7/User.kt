package com.example.myprojectteam7

import android.os.Build
import androidx.annotation.RequiresApi
import com.google.firebase.database.Exclude
import java.time.LocalDate
import java.time.format.DateTimeFormatter


//날짜 기본 값
const val UNCHECKED_DATE = "2022-11-11"

//유저 클래스
@RequiresApi(Build.VERSION_CODES.O)
data class User(var username: String? = "",
                var usernumber: String? = "",
                var userpassword: String? = "",
                var viewDate: LocalDate? = LocalDate.parse(UNCHECKED_DATE, DateTimeFormatter.ISO_DATE),
                var viewTodo: String? = "")
{
    //User -> Map
    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "username" to username,
            "usernumber" to usernumber,
            "userpassword" to userpassword,
            "viewDate" to viewDate.toString(),
            "viewTodo" to viewTodo
        )
    }

    //Map -> User
    fun toUser(value: Any?) :User {
        if(value == null)
            return User()
        val map = value as Map<String, Any?>
        val user = User()
        user.let {
            it.username = map.get("username") as String? ?: ""
            it.usernumber = map.get("usernumber") as String? ?: ""
            it.userpassword = map.get("userpassword") as String? ?: ""
            it.viewDate = LocalDate.parse(map.get("viewDate") as String? ?: "", DateTimeFormatter.ISO_DATE)
            it.viewTodo = map.get("viewTodo") as String? ?: ""
        }
        return user
    }
}