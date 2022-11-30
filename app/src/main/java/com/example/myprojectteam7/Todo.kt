package com.example.myprojectteam7

import android.os.Build
import androidx.annotation.RequiresApi
import com.google.firebase.database.Exclude
import java.time.LocalDate
import java.time.format.DateTimeFormatter

//일정 -> date1:날짜, uid:유저아이디, author: 작성자(유저네임), title: 제목, memo: 메모, time: 시간
//아이디, 제목, 날짜, 메모
const val UNCHECKED_DATE = "2022-11-11"
@RequiresApi(Build.VERSION_CODES.O)
data class Todo(
    var uid: String? = "",
    var title: String? = "",
    var date: LocalDate? = LocalDate.parse(UNCHECKED_DATE, DateTimeFormatter.ISO_DATE),
    var memo: String? = "",
    var key: String? = "",
    var location: String? = ""
) {
    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "uid" to uid,
            "title" to title,
            "date" to date.toString(),
            "memo" to memo,
            "key" to key,
            "location" to location
        )
    }
}