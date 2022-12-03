package com.example.myprojectteam7

import android.os.Build
import androidx.annotation.RequiresApi
import com.google.firebase.database.Exclude
import java.time.LocalDate
import java.time.format.DateTimeFormatter

//일정 -> date1:날짜, uid:유저아이디, author: 작성자(유저네임), title: 제목, memo: 메모, time: 시간
//아이디, 제목, 날짜, 메모
@RequiresApi(Build.VERSION_CODES.O)
data class Todo(
    var uid: String? = "",
    var title: String? = "",
    var date: LocalDate? = LocalDate.parse(UNCHECKED_DATE, DateTimeFormatter.ISO_DATE),
    var memo: String? = "",
    var location: String? = "",
    var key: String? = ""
) {
    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "uid" to uid,
            "title" to title,
            "date" to date.toString(),
            "memo" to memo,
            "location" to location,
            "key" to key
        )
    }
}