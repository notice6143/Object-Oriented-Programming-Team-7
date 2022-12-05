package com.example.myprojectteam7

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.Dimension
import androidx.annotation.RequiresApi
import androidx.core.os.bundleOf
import androidx.lifecycle.LiveData
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myprojectteam7.bin.ViewCalendar
import com.example.myprojectteam7.databinding.ListDayBinding
import com.example.myprojectteam7.viewmodel.CalendarsViewModel

//캘린더 날짜 어답터, 중첩리사이클러뷰 -> 날짜어답터안에 리스트어답터 추가
@RequiresApi(Build.VERSION_CODES.O)
class CalendarAdapter(val cals: LiveData<ArrayList<ViewCalendar>>, val viewModel: CalendarsViewModel): RecyclerView.Adapter<CalendarAdapter.ViewHolder>() {

    class ViewHolder(val binding: ListDayBinding, val viewModel: CalendarsViewModel) : RecyclerView.ViewHolder(binding.root) {

        fun bind(cal: ViewCalendar?) {
            cal?.let {

                binding.txtDay1.text = it.date1.dayOfMonth.toString()

                binding.txtDay1.setTextColor(
                    when(cal.date1.monthValue) {
                        viewModel.month -> R.color.black
                        else -> R.color.white_20
                    }
                )

                binding.date.setBackgroundResource(
                    when (cal.date1.monthValue) {
                        viewModel.month -> R.drawable.ic_baseline_date
                        else -> R.drawable.ic_baseline_date_weekend
                    }
                )

                //월이 일치하지않으면 실행
                if(viewModel.month!=it.date1.monthValue) {
                    //첫날, 마지막날 달 표시
                    if(it.date1.dayOfMonth == 1 || it.date1.dayOfMonth==it.date1.lengthOfMonth())
                        binding.txtDay1.text = "${it.date1.dayOfMonth}\n${it.date1.month.toString().substring(0 .. 2)}"

                    binding.txtDay1.setTextSize(Dimension.SP,10F)
                }
                else {
                    binding.txtDay1.setTextSize(Dimension.SP,14F)
                }

                //일정 클릭
                binding.date.setOnClickListener { view ->
                    //선택한 날짜로 포인터
                    viewModel.setViewDate(it.date1)
                    view.findNavController().navigate(R.id.action_calendarFragment_to_todolistFragment)
                }

                //중첩리사이클러뷰
                binding?.recList?.layoutManager = LinearLayoutManager(binding.recList.context)
                binding?.recList?.adapter = CalendarListAdapter(cal.todolist, viewModel.phone)

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ListDayBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding, viewModel)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(cals.value?.getOrNull(position))
    }

    override fun getItemCount() = cals.value?.size ?: 0

}