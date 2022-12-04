package com.example.myprojectteam7

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.myprojectteam7.databinding.FriendlistItemBinding
import com.example.myprojectteam7.viewmodel.CalendarsViewModel
import com.google.firebase.database.FirebaseDatabase

@RequiresApi(Build.VERSION_CODES.O)
class FriendItemAdapter(
    val lists: LiveData<ArrayList<Friend>>,
    val viewModel: CalendarsViewModel)
: RecyclerView.Adapter<FriendItemAdapter.ViewHolder>(){

    class ViewHolder(val binding: FriendlistItemBinding,val viewModel: CalendarsViewModel)
        : RecyclerView.ViewHolder(binding.root){
        fun bind(friend: Friend?){
            friend?.let {
                binding.friendlist.setBackgroundResource(
                when(friend.fid ?: viewModel.phone){
                    viewModel.phone -> R.drawable.ic_baseline_none_rect
                    else -> R.drawable.frdlist_item
                })

                binding.txtFrdname.text = it.fid
                binding.btnFrddelete.setOnClickListener { view ->
                    viewModel.deleteFriend(it.fid)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = FriendlistItemBinding.inflate(LayoutInflater.from(parent.context))
        return ViewHolder(binding,viewModel)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        lists.value?.getOrNull(position)?.let { holder.bind(it) }

        /*holder.binding.btnFrddelete.setOnClickListener{
            viewModel.deleteFriend(position)
        }

         */

    }

    override fun getItemCount() = lists.value?.size ?: 0
}