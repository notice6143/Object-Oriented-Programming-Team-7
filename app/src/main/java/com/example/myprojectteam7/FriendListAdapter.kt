package com.example.myprojectteam7

import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.os.bundleOf
import androidx.lifecycle.LiveData
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.myprojectteam7.databinding.ListFriendBinding
import com.example.myprojectteam7.databinding.ListTodolistBinding
import com.example.myprojectteam7.viewmodel.CalendarsViewModel


//친구 리사이클러뷰
@RequiresApi(Build.VERSION_CODES.O)
class FriendListAdapter(val lists: LiveData<ArrayList<Friend>>): RecyclerView.Adapter<FriendListAdapter.ViewHolder>() {

    class ViewHolder(val binding: ListFriendBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(friend: Friend?) {
            friend?.let {
                binding.txtFriend.text = it.fid


            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ListFriendBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(lists.value?.getOrNull(position))
    }

    override fun getItemCount() = lists.value?.size ?: 0
}