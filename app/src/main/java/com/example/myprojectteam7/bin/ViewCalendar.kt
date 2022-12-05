package com.example.myprojectteam7.bin

import java.time.LocalDate

//달력표시 -> 일자와 일정리스트
data class ViewCalendar(
    val date1: LocalDate,
    val todolist: ArrayList<Todo>
)