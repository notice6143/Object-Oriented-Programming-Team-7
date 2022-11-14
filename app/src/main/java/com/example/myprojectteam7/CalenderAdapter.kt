package com.example.myprojectteam7

import android.icu.util.Calendar
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myprojectteam7.databinding.ActivityCalendarBinding
import com.example.myprojectteam7.databinding.ListDayBinding

class CalenderAdapter(val mycal: Mycalender)
    : RecyclerView.Adapter<CalenderAdapter.Holder>() {
    val temp = 0

    val calender_max_size = 35

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = ListDayBinding.inflate(LayoutInflater.from(parent.context))
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(mycal.dayList[position])
    }

    override fun getItemCount() = mycal.dayList.size

    class Holder(val binding: ListDayBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(day: Day){
            binding.txtDay.text = day.date

        }
    }
}