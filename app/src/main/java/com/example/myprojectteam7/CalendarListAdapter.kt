package com.example.myprojectteam7

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.myprojectteam7.bin.Todo
import com.example.myprojectteam7.databinding.ListRectBinding

//캘린더 날짜 어답터, 중첩리사이클러뷰 -> 날짜어답터안에 리스트어답터 추가
//달력에 일정 표시
@RequiresApi(Build.VERSION_CODES.O)
class CalendarListAdapter(val lists: ArrayList<Todo>, val phone: String): RecyclerView.Adapter<CalendarListAdapter.ViewHolder>() {

    class ViewHolder(val binding: ListRectBinding, val phone: String) : RecyclerView.ViewHolder(binding.root) {
        fun bind(todo: Todo?) {
            todo?.let {

                binding.imgRect.setBackgroundResource(
                    when (todo?.uid ?: phone) {
                        phone -> R.drawable.ic_baseline_me_rect
                        else -> R.drawable.ic_baseline_friend_rect
                    }
                )

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ListRectBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding, phone)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(lists?.getOrNull(position))
    }

    override fun getItemCount() = lists.size
}