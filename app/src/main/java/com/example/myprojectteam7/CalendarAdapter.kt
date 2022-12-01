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

    class ViewHolder(val binding: ListDayBinding, val nowMonth: Int, val phone: String) : RecyclerView.ViewHolder(binding.root) {
        fun bind(cal: ViewCalendar?) {
            cal?.let {
                val viewModel = CalendarsViewModel(phone)

                //월이 일치하면 실행
                if(nowMonth==it.date1.monthValue) {
                    binding.txtDay1.text = it.date1.dayOfMonth.toString()
                    binding.date.setBackgroundResource(
                        when (cal.date1.monthValue) {
                            nowMonth -> R.drawable.ic_baseline_date
                            else -> R.drawable.ic_baseline_date_disabled
                        }
                    )

                    //일정 클릭
                    binding.date.setOnClickListener { view ->
                        viewModel.setViewDate(it.date1)
                        val bundle = bundleOf("Phone" to phone)
                        view.findNavController().navigate(R.id.action_calendarFragment_to_todolistFragment, bundle)
                    }

                    binding.txtDay1.setOnClickListener { view ->
                        //선택한 날짜로 포인터
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

}