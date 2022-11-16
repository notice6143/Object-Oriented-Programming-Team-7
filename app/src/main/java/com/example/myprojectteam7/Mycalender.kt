package com.example.myprojectteam7

import java.util.Calendar

class Mycalender( var year: Int,  var month: Int) {

    val cal = Calendar.getInstance()
    val dayOfWeek = Calendar.DAY_OF_WEEK
    val date = cal.get(Calendar.DATE)
    val firstday = (dayOfWeek + 8 - date % 7) % 7
    var maxDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH)

    val dayList = makeDayList()
    val weekDayList = weekList(dayList)

    init {
    }

    fun makeDayList(): List<Day>{
        val temp = mutableListOf<Day>()
        var idx = 1

        for(i in 1 until firstday){
            temp.add(Day(""))
        }
        for(i in firstday .. maxDay){
            temp.add(Day("$idx"))
            idx++
        }
        for(i in maxDay+1 .. 35){
            temp.add(Day(""))
        }
        return temp
    }

    fun weekList(daylist:List<Day>): List<List<Day>>{
        val temp1 = mutableListOf<Day>()
        val temp2 = mutableListOf<Day>()
        val temp3 = mutableListOf<Day>()
        val temp4 = mutableListOf<Day>()
        val temp5 = mutableListOf<Day>()
        val answer = mutableListOf<List<Day>>()

        for(i in 0 .. 6){
            temp1.add(daylist[i])
        }
        for(i in 7 .. 13){
            temp2.add(daylist[i])
        }
        for(i in 14 .. 20){
            temp3.add(daylist[i])
        }
        for(i in 21 .. 27){
            temp4.add(daylist[i])
        }
        for(i in 28 .. 34){
            temp5.add(daylist[i])
        }

        answer.add(temp1)
        answer.add(temp2)
        answer.add(temp3)
        answer.add(temp4)
        answer.add(temp5)

        return answer
    }
}