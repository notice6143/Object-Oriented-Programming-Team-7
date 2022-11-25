package com.example.myprojectteam7

import android.os.Build
import androidx.annotation.RequiresApi
import com.google.firebase.database.Exclude
import java.time.LocalDate
import java.time.format.DateTimeFormatter

//친구 -> fid: 친구아이디, uid:유저아이디, key:파이어베이스key
@RequiresApi(Build.VERSION_CODES.O)
data class Friend(
    var uid: String? = "",
    var fid: String? = "",
    var key: String? = ""
) {
    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "uid" to uid,
            "fid" to fid,
            "key" to key
        )
    }
}