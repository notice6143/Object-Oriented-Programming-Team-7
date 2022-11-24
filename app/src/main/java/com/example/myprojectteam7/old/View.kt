package com.example.myprojectteam7.old

import android.os.Build
import androidx.annotation.RequiresApi
import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties

//Calendar RecyclerView
//파이어베이스를 맵핑할 때 번지수 확인
@IgnoreExtraProperties
@RequiresApi(Build.VERSION_CODES.O)
data class View(
    var year: String? = "",
    var month: String? = "",
    var day: String? = "",
    var index: MutableMap<String, String> = HashMap(),
    var date: String? = ""
) {
    init {
        date = "${year}-${month}-${day}"
    }
    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "date" to date,
            "year" to year,
            "month" to month,
            "day" to day,
            "index" to index
        )
    }
}