package com.example.myprojectteam7

import android.icu.util.Calendar
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.os.bundleOf
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.myprojectteam7.databinding.ListDayBinding

@RequiresApi(Build.VERSION_CODES.O)
class CalenderAdapter(val myCal:Mycalendar): RecyclerView.Adapter<CalenderAdapter.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = ListDayBinding.inflate(LayoutInflater.from(parent.context))
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(myCal.week[position], myCal)
    }

    override fun getItemCount() = myCal.week.size

    class Holder(val binding: ListDayBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(week: Week?, myCal: Mycalendar) {

            binding.txtDay1.text = if (week?.day1 != 0) week?.day1.toString() else ""

            binding.txtDay1.setOnClickListener { view ->
                val bundle = bundleOf("Calendar" to myCal)
                view.findNavController().navigate(R.id.action_calendarFragment_to_todolistFragment, bundle)
            }

        }
    }
}