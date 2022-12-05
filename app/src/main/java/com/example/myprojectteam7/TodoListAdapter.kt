package com.example.myprojectteam7

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.os.bundleOf
import androidx.lifecycle.LiveData
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.myprojectteam7.bin.Todo
import com.example.myprojectteam7.databinding.ListTodolistBinding
import com.example.myprojectteam7.viewmodel.CalendarsViewModel

//캘린더 날짜 어답터, 중첩리사이클러뷰 -> 날짜어답터안에 리스트어답터 추가
@RequiresApi(Build.VERSION_CODES.O)
class TodoListAdapter(val lists: LiveData<ArrayList<Todo>>, val viewModel: CalendarsViewModel): RecyclerView.Adapter<TodoListAdapter.ViewHolder>() {

    class ViewHolder(val binding: ListTodolistBinding, val viewModel: CalendarsViewModel) : RecyclerView.ViewHolder(binding.root) {
        fun bind(todo: Todo?) {
            todo?.let {
                binding.todolist.setBackgroundResource(
                    when (todo?.uid ?: viewModel.phone) {
                        viewModel.phone -> R.drawable.ic_baseline_me_todolist
                        else -> R.drawable.ic_baseline_friend_todolist
                    }
                )
                binding.txtTitle.text = it.title
                binding.txtDate.text = it.date.toString()
                binding.txtUid.text = it.uid


                binding.todolist.setOnClickListener { view ->
                    //선택한 Me or Friend ID 확인
                    viewModel.setViewTodo(it)
                    view.findNavController().navigate(R.id.action_todolistFragment_to_tododetailFragment)
                }

                binding.btnDelTodo.setOnClickListener {view ->
                    viewModel.deleteTodo(it)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ListTodolistBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding, viewModel)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(lists.value?.getOrNull(position))
    }

    override fun getItemCount() = lists.value?.size ?: 0
}