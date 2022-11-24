package com.example.myprojectteam7

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.os.bundleOf
import androidx.lifecycle.LiveData
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myprojectteam7.databinding.ListDayBinding
import com.example.myprojectteam7.viewmodel.CalendarsViewModel

//캘린더 날짜 어답터, 중첩리사이클러뷰 -> 날짜어답터안에 리스트어답터 추가
@RequiresApi(Build.VERSION_CODES.O)
class CalendarAdapter(val cals: LiveData<ArrayList<ViewCalendar>>, val phone: String): RecyclerView.Adapter<CalendarAdapter.ViewHolder>() {
    //val month = dates.value.get(10).monthValue

    class ViewHolder(val binding: ListDayBinding, val nowMonth: Int, val phone: String) : RecyclerView.ViewHolder(binding.root) {
        fun bind(cal: ViewCalendar?) {
            cal?.let {
                val viewModel = CalendarsViewModel(phone)

                //월이 일치하면 실행
                if(nowMonth==it.date1.monthValue) {
                    binding.txtDay1.text = it.date1.dayOfMonth.toString()
                    binding.date.setBackgroundResource(
                        when (cal.date1.monthValue) {
                            nowMonth -> R.drawable.ic_baseline_date_disabled
                            else -> R.drawable.ic_baseline_date
                        }
                    )
                    binding.date.setOnClickListener { view ->
                        viewModel.setViewDate(it.date1)
                        val bundle = bundleOf("Phone" to phone)
                        view.findNavController().navigate(R.id.action_calendarFragment_to_todolistFragment, bundle)
                    }

                    binding.txtDay1.setOnClickListener { view ->
                        viewModel.setViewDate(it.date1)
                        val bundle = bundleOf("Phone" to phone)
                        view.findNavController().navigate(R.id.action_calendarFragment_to_todolistFragment, bundle)
                    }

                    //중첩리사이클러뷰
                    binding?.recList?.layoutManager = LinearLayoutManager(binding.recList.context)
                    binding?.recList?.adapter = CalendarListAdapter(cal.todolist, phone)
                }

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ListDayBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val cal = cals?.value?.get(10)
        val nowMonth = cal?.date1?.monthValue ?: 0
        return ViewHolder(binding, nowMonth, phone)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(cals.value?.getOrNull(position))
    }

    override fun getItemCount() = cals.value?.size ?: 0
    /*
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

     */
}