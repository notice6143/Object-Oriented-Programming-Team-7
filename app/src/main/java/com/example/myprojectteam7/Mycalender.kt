package com.example.myprojectteam7

import java.util.Calendar

class Mycalender( var year: Int,  var month: Int, var firstday: Int ) {

    val cal = Calendar.getInstance()
    var maxDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH)

    val dayList = makeDayList()
    init {
    }

    fun makeDayList(): List<Day>{
        val temp = mutableListOf<Day>()
        val idx = 1

        for(i in 1 until firstday){
            temp.add(Day(""))
        }
        for(i in firstday .. maxDay){
            temp.add(Day("$idx"))
        }
        for(i in maxDay+1 .. 35){
            temp.add(Day(""))
        }
        return temp
    }

}