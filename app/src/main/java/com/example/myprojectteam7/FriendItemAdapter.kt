package com.example.myprojectteam7

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.myprojectteam7.databinding.FriendlistItemBinding


class FriendItemAdapter(
    val lists: LiveData<ArrayList<Friend>>,
    val phone: String
    //val onClickDelete: () -> Unit,
    //function: () -> Unit
)
: RecyclerView.Adapter<FriendItemAdapter.ViewHolder>(){
    class ViewHolder(val binding: FriendlistItemBinding,val phone: String): RecyclerView.ViewHolder(binding.root){
        fun bind(friend: Friend){
            if(friend.fid != "")
                binding.txtFrdname.text = friend.fid

            binding.btnFrddelete.setOnClickListener {
                friend.fid = ""
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = FriendlistItemBinding.inflate(LayoutInflater.from(parent.context))
        return ViewHolder(binding,phone)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        lists.value?.getOrNull(position)?.let { holder.bind(it) }
        /*
        holder.binding.btnFrddelete.setOnClickListener{

            onClickDelete.invoke()
        }

         */
    }

    override fun getItemCount() = lists.value?.size ?: 0
}