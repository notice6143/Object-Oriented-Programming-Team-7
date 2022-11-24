package com.example.myprojectteam7


import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.myprojectteam7.databinding.ListScheduleBinding
import com.example.myprojectteam7.old.Mycalendar
import com.example.myprojectteam7.old.Todolists

@RequiresApi(Build.VERSION_CODES.O)
class TodolistAdapter(val myCal: Mycalendar): RecyclerView.Adapter<TodolistAdapter.Holder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = ListScheduleBinding.inflate(LayoutInflater.from(parent.context))
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(myCal.todolist[position], myCal)
    }

    override fun getItemCount() = myCal.listcount

    class Holder(val binding: ListScheduleBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(list: Todolists?, myCal: Mycalendar) {
            val index=myCal.didx.indexOf(myCal.day.toInt())
            binding.txtTitle.text = myCal.tidx[index]
        }
    }
}