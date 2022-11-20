package com.example.myprojectteam7

enum class Schedule {
    ME, FRIEND, NONE
}

data class Days(val day1: Int,
                val skds: Schedule)