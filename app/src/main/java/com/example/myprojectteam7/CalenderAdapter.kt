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
import java.time.LocalDate

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
        fun bind(days: Days?, myCal: Mycalendar) {
                binding.date.setBackgroundResource(
                    when(days?.day1 ?: 0) {
                        0 -> R.drawable.ic_baseline_date_disabled
                        else -> R.drawable.ic_baseline_date
                    }
                )
                binding.meSchedule.setBackgroundResource(
                    when (days?.skds ?: Schedule.NONE) {
                        Schedule.ME -> R.drawable.ic_baseline_me_rect
                        Schedule.FRIEND -> R.drawable.ic_baseline_me_rect
                        Schedule.NONE -> R.drawable.ic_baseline_none_rect
                    }
                )
                binding.txtDay1.text = if (days?.day1 != 0) days?.day1.toString() else ""
            if (days?.day1 != 0) {
                binding.txtDay1.setOnClickListener { view ->
                    val date: LocalDate =
                        LocalDate.of(myCal.year.toInt(), myCal.month.toInt(), days?.day1 ?: 1)
                    val putCal: Mycalendar = Mycalendar(date, myCal.id, myCal.idx)
                    val bundle = bundleOf("Calendar" to putCal)
                    view.findNavController()
                        .navigate(R.id.action_calendarFragment_to_todolistFragment, bundle)
                }
            }
        }
    }
}