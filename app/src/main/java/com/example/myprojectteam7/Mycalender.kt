package com.example.myprojectteam7

import java.util.Calendar

class Mycalender( var year: Int,  var month: Int) {
    //달을 옮기려면 Calender.set()쓰면 된다고 합니다
    val cal = Calendar.getInstance()
    var dayOfWeek = 0

    init {
        cal.set(year, month, cal.get(Calendar.DATE))
        dayOfWeek = cal.get(Calendar.DAY_OF_WEEK)
    }

    val firstday = (dayOfWeek + 8 - cal.get(Calendar.DATE) % 7) % 7
    var maxDay = cal.getMaximum(Calendar.DAY_OF_MONTH)  //달 마다 요일 수 구하기

    val dayList = makeDayList()
    val weekDayList = weekList(dayList)


    fun makeDayList(): List<Day>{
        val temp = mutableListOf<Day>()
        var idx = 1

        for(i in 1 until firstday){
            temp.add(Day(""))
        }
        for(i in 1 .. maxDay){
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