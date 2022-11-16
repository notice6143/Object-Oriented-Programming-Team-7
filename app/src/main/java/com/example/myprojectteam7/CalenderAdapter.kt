package com.example.myprojectteam7

import android.icu.util.Calendar
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myprojectteam7.databinding.ActivityCalendarBinding
import com.example.myprojectteam7.databinding.ListDayBinding

class CalenderAdapter(val mycal: Mycalender)
    : RecyclerView.Adapter<CalenderAdapter.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = ListDayBinding.inflate(LayoutInflater.from(parent.context))
        return Holder(binding)
    }


    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(mycal.weekDayList[position])
    }

    override fun getItemCount() = 35

    class Holder(val binding: ListDayBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(day: List<Day>){
            binding.txtSunday.text = day[0].date
            binding.txtMonday.text = day[1].date
            binding.txtTuesday.text = day[2].date
            binding.txtWensday.text = day[3].date
            binding.txtThursday.text = day[4].date
            binding.txtFriday.text = day[5].date
            binding.txtSaturday.text = day[6].date

        }
    }
}