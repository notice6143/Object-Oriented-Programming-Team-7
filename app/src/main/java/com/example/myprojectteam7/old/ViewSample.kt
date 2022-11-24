package com.example.myprojectteam7.old

import android.os.Build
import androidx.annotation.RequiresApi
import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties
import java.time.LocalDate
import java.time.format.DateTimeFormatter

//파이어베이스를 맵핑할 때 번지수 확인
@IgnoreExtraProperties
@RequiresApi(Build.VERSION_CODES.O)
data class ViewSample(
    var uid: String? = "",
    var author: String? = "",
    var title: String? = "",
    var body: String? = "",
    var starCount: Int = 0,
    var stars: MutableMap<String, Boolean> = HashMap()
) {
    val date = LocalDate.now()
    val year = date.format(DateTimeFormatter.ofPattern("yyyy")).toString()
    val month = date.format(DateTimeFormatter.ofPattern("MM")).toString()
    val day = date.format(DateTimeFormatter.ofPattern("dd")).toString()
    val yym = date.format(DateTimeFormatter.ofPattern("yyyy/MM")).toString()
    val firstday: LocalDate = LocalDate.of(year.toInt() ,month.toInt(),1)
    val int_list :ArrayList<Int> get() {
        var temp = ArrayList<Int>()
        int_list.add(1)
        int_list.add(2)
        int_list.add(3)
        return temp
    }


    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "uid" to uid,
            "author" to author,
            "title" to title,
            "body" to body,
            "starCount" to starCount,
            "stars" to stars,
            year to day
        )
    }
}