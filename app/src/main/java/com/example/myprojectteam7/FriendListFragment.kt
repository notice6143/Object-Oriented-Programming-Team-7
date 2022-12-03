package com.example.myprojectteam7

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.os.bundleOf
import androidx.core.view.marginRight
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myprojectteam7.databinding.FragmentFriendlistBinding
import com.example.myprojectteam7.viewmodel.CalendarsViewModel
//import kotlinx.android.synthetic.main.list_friend.*

class FriendListFragment : Fragment(){

    val viewModel: CalendarsViewModel by activityViewModels()
    var binding: FragmentFriendlistBinding? = null
    var phone: String = ""
    //val lists = LiveData<ArrayList<Friend>>()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            phone = it.getString("Phone") as String
        }
        /*binding?.recFrdlist?.layoutManager = LinearLayoutManager(requireContext())
        binding?.recFrdlist?.adapter = FriendItemAdapter(viewModel.friend,phone, onClickDelete = {
            deleteTask(viewModel.friend.value.get(super))
        })

         */
    }

    fun deleteTask(friend: Friend){
        friend.fid = ""
        /*
        viewModel.friend.remove(friend)
        binding?.recFrdlist?.adapter?.notifyDataSetChanged()
         */
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFriendlistBinding.inflate(inflater)
        return binding?.root
    }

    @SuppressLint("NotifyDataSetChanged")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.friend.observe(viewLifecycleOwner){
            binding?.recFrdlist?.adapter?.notifyDataSetChanged()
        }
        binding?.recFrdlist?.layoutManager = LinearLayoutManager(context)
        binding?.recFrdlist?.adapter = FriendItemAdapter(viewModel.friend, phone)

        binding?.btnBacktocal?.setOnClickListener{
            val bundle = bundleOf("Phone" to phone)
            findNavController().navigate(R.id.action_friendListFragment_to_calendarFragment,bundle)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}