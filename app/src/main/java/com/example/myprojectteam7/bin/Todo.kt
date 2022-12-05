package com.example.myprojectteam7.bin

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
    var author: String? = "",
    var title: String? = "",
    var date: LocalDate? = LocalDate.parse(UNCHECKED_DATE, DateTimeFormatter.ISO_DATE),
    var memo: String? = "",
    var location: String? = "",
    var key: String? = ""
) {
    //Todo -> Map
    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "uid" to uid,
            "author" to author,
            "title" to title,
            "date" to date.toString(),
            "memo" to memo,
            "location" to location,
            "key" to key
        )
    }

    //Map -> Todo
    fun toTodo(value: Any?) : Todo {
        if(value == null)
            return Todo()

        val map = value as Map<String, Any?>
        val todo = Todo()
        todo.let {
            it.uid = map.get("uid") as String? ?: ""
            it.title = map.get("title") as String? ?: ""
            it.author = map.get("author") as String? ?: ""
            it.date = LocalDate.parse(map.get("date") as String? ?: "", DateTimeFormatter.ISO_DATE)
            it.memo = map.get("memo") as String? ?: ""
            it.location = map.get("location") as String? ?: ""
            it.key = map.get("key") as String? ?: ""
        }
        return todo
    }
}