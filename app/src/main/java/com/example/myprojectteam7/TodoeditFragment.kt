package com.example.myprojectteam7

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.example.myprojectteam7.databinding.FragmentTodoeditBinding

@RequiresApi(Build.VERSION_CODES.O)
class TodoeditFragment : Fragment() {
    private var year: String = ""
    private var month: String = ""
    private var id: String = ""
    lateinit var myCal: Mycalendar
    var binding: FragmentTodoeditBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            myCal = it.getSerializable("Calendar") as Mycalendar
            id = myCal.id
            year = myCal.year
            month = myCal.month
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_todoedit, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.btnClose2?.setOnClickListener {
            val bundle = bundleOf("Calendar" to myCal)
            findNavController().navigate(R.id.action_todoeditFragment_to_todolistFragment,bundle)
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}