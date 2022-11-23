package com.example.myprojectteam7

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.setFragmentResult
import com.example.myprojectteam7.databinding.FragmentSettingsBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

class SettingsFragment : Fragment() {

    lateinit var binding: FragmentSettingsBinding
    //private var value = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSettingsBinding.inflate(inflater,container,false)

        binding.changeName.setOnKeyListener { view, i, keyEvent ->
            editSelf(1,binding.changeName.text.toString())
            false
        }
        binding.changeEmail.setOnKeyListener { view, i, keyEvent ->
            editSelf(2,binding.changeEmail.text.toString())
            false
        }
        //binding.

        return binding.root
    }
    fun editSelf(index: Int, part: String){
        val change = Bundle().apply {
            putString("value${index}",part)
        }
        setFragmentResult("value",change)
    }

    companion object{
        fun newInstance() =
            SettingsFragment()
    }

    /*companion object {
        fun newInstance(param1: String, param2: String) =
            SettingsFragment().apply {
                
            }
    }
     */
}