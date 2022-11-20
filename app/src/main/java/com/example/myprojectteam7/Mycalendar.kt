package com.example.myprojectteam7

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import java.io.Serializable
import java.time.LocalDate
import java.time.Month
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
data class Mycalendar(val date : LocalDate, val id:String): Serializable {
    var year = date.format(DateTimeFormatter.ofPattern("yyyy")).toString()
    var month = date.format(DateTimeFormatter.ofPattern("MM")).toString()
    var firstday: LocalDate = LocalDate.of(year.toInt() ,month.toInt(),1)
    var firstdayOfWeek = if(firstday.dayOfWeek.value!=7) firstday.dayOfWeek.value else 0
    var monthSize = firstday.lengthOfMonth()
    var arrWeek = daylist()
    var week = weeklist(arrWeek)
    var monthStr = date.month.toString()
    fun daylist() :IntArray {
        var temp = IntArray(43)
        for (i in firstdayOfWeek .. monthSize+firstdayOfWeek-1) {
            temp.set(i, i - firstdayOfWeek + 1)
        }
        return temp
    }
    fun weeklist(arr:IntArray): Array<Week?> {
        var temp=Array<Week?>(35){null}
        for(i in 0..34) {
            temp[i]=Week(arr[i])
        }
        return temp
    }
}