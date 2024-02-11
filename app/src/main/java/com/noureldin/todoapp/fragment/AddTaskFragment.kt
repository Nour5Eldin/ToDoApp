package com.noureldin.todoapp.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.noureldin.todoapp.databinding.FragmentAddTaskBinding

class AddTaskFragment : Fragment() {
private lateinit var binding: FragmentAddTaskBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding= FragmentAddTaskBinding.inflate(layoutInflater)
        // Inflate the layout for this fragment
        return binding.root
    }

}