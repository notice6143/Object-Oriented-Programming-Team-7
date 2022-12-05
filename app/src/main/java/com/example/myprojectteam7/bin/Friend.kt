package com.example.myprojectteam7.bin

import android.os.Build
import androidx.annotation.RequiresApi
import com.google.firebase.database.Exclude


//친구 -> fid: 친구아이디, uid:유저아이디
@RequiresApi(Build.VERSION_CODES.O)
data class Friend(
    var uid: String? = "",
    var fid: String? = ""
) {
    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "uid" to uid,
            "fid" to fid
        )
    }

    fun toFriend(value: Any?) : Friend {
        if(value == null)
            return Friend()

        val map = value as Map<String, Any?>
        val friend = Friend()
        friend.let {
            it.uid = map.get("uid") as String? ?: ""
            it.fid = map.get("fid") as String? ?: ""
        }
        return friend
    }
}