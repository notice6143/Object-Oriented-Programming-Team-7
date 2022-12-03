package com.example.myprojectteam7

import android.os.Build
import androidx.annotation.RequiresApi
import com.google.firebase.database.Exclude
import java.time.LocalDate
import java.time.format.DateTimeFormatter


const val UNCHECKED_DATE = "2022-11-11"
@RequiresApi(Build.VERSION_CODES.O)
data class User(val username: String? = null,
                val usernumber: String? = null,
                val userpassword: String? = null,
                val viewDate: LocalDate? = LocalDate.parse(UNCHECKED_DATE, DateTimeFormatter.ISO_DATE),
                val viewTodo: String? = null)
{
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
}