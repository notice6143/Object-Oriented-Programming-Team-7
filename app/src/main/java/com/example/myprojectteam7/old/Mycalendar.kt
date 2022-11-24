package com.example.myprojectteam7.old

import android.os.Build
import androidx.annotation.RequiresApi
import java.io.Serializable
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
data class Mycalendar(val date : LocalDate, val id:String, val idx: HashMap<String,String>): Serializable {
    //날짜
    //var date = LocalDate.parse(Str, DateTimeFormatter.ISO_DATE)
    val year = date.format(DateTimeFormatter.ofPattern("yyyy")).toString()
    val month = date.format(DateTimeFormatter.ofPattern("MM")).toString()
    val day = date.format(DateTimeFormatter.ofPattern("dd")).toString()
    val yym = date.format(DateTimeFormatter.ofPattern("yyyy/MM")).toString()
    val firstday: LocalDate = LocalDate.of(year.toInt() ,month.toInt(),1)
    val firstdayOfWeek = if(firstday.dayOfWeek.value!=7) firstday.dayOfWeek.value else 0
    val monthSize = firstday.lengthOfMonth()
    val monthStr = date.month.toString().substring(0 ..2)

    //Index
    val didx = getDIndex(idx)
    val tidx = getTIndex(idx)

    //리사이클러뷰
    val arrWeek = daylist()
    val week = weeklist(arrWeek)
    val listcount = didx.count {it==day.toInt()}
    val todolist = todoList(tidx,didx,didx.size)

    //1~31일 날짜 배열
    fun daylist() :IntArray {
        var temp = IntArray(42)
        for (i in firstdayOfWeek .. monthSize+firstdayOfWeek-1) {
            temp.set(i, i - firstdayOfWeek + 1)
        }
        return temp
    }

    //리사이클러뷰 달력객체생성
    fun weeklist(arr:IntArray): Array<Days?> {
        var temp=Array<Days?>(42){null}
        for(i in 0..41) {
            if(didx.contains(arr[i]))
                temp[i]= Days(arr[i], Schedule.ME)
            else
                temp[i]= Days(arr[i], Schedule.NONE)
        }
        return temp
    }

    //리사이클러뷰 스케줄객체생성
    fun todoList(arrt: ArrayList<String>,arrd: ArrayList<Int>,size: Int): Array<Todolists?> {
        var temp = Array<Todolists?>(size){null}
        for(i in 0 .. size - 1) {
            temp[i]= Todolists(i,arrt[i],arrd[i])
        }
        return temp
    }

    //해쉬맵 분리 -> 인덱스
    //Day Index
    fun getDIndex(arr: HashMap<String,String>): ArrayList<Int> {
        var temp = ArrayList<Int>()
        for((i, j) in arr)
            temp.add(j.toInt())
        return temp
    }
    //Title Index
    fun getTIndex(arr: HashMap<String,String>): ArrayList<String> {
        var temp = ArrayList<String>()
        for((i, j) in arr)
            temp.add(i)
        return temp
    }

}