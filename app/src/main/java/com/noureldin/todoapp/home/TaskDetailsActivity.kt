package com.noureldin.todoapp.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.noureldin.todoapp.R
import com.noureldin.todoapp.databinding.ActivityTaskDetailsBinding

class TaskDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTaskDetailsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTaskDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}