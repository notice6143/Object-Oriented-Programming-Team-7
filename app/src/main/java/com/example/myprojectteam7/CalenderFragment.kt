package com.example.myprojectteam7

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.myprojectteam7.databinding.FragmentCalenderBinding

class CalenderFragment: Fragment(){
    private var value: String? = ""

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        arguments?.let{
            value = it.getString("value")
        }
    }

    lateinit var binding: FragmentCalenderBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCalenderBinding.inflate(inflater,container,false)

        binding.txtNow.text = value.toString()
        return binding.root
    }

    companion object{

        fun newInstance(value: Int)=
            CalenderFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}