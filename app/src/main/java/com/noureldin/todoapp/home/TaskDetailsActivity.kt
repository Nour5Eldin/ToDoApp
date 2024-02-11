package com.noureldin.todoapp.home

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.noureldin.todoapp.R
import com.noureldin.todoapp.dao.TasksDao
import com.noureldin.todoapp.databinding.ActivityTaskDetailsBinding
import com.noureldin.todoapp.db.TaskDataBase
import com.noureldin.todoapp.model.Task
import com.noureldin.todoapp.utils.Constants
import com.noureldin.todoapp.utils.getHourIn12
import com.noureldin.todoapp.utils.getTimeAmPm
import com.noureldin.todoapp.utils.parcelable
import com.noureldin.todoapp.utils.showDatePickerDialog
import com.noureldin.todoapp.utils.showTimePickerDialog
import java.util.Calendar

class TaskDetailsActivity : AppCompatActivity() {
    lateinit var binding: ActivityTaskDetailsBinding
    lateinit var Dao: TasksDao
    private var dateCalender = Calendar.getInstance()
    private var timePicker = Calendar.getInstance()
    private var MyTask = Task()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTaskDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViews()
        onSaveClick()
        onSelectDateClickListener()
        onSelectTimeClickListener()
        setupToolBar()
    }

    private fun setupToolBar() {
           setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar
        binding.toolbar.setNavigationOnClickListener {
            finish()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun onSelectTimeClickListener() {
        binding.content.selectTimeTil.setOnClickListener {
            val calendar = Calendar.getInstance()
            showTimePickerDialog(
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                title = "Select task time",
                supportFragmentManager
            ) { hour, minutes ->
                val minuteString = if (minutes == 0) "00" else minutes.toString()
                binding.content.selectTimeTv.text =
                    "${getHourIn12(hour)}:${minuteString} ${getTimeAmPm(hour)}"
                this.timePicker.set(Calendar.YEAR, 0)
                this.timePicker.set(Calendar.MONTH, 0)
                this.timePicker.set(Calendar.DAY_OF_MONTH, 0)
                this.timePicker.set(Calendar.HOUR_OF_DAY, hour)
                this.timePicker.set(Calendar.MINUTE, minutes)
                this.timePicker.set(Calendar.SECOND, 0)
                this.timePicker.set(Calendar.MILLISECOND, 0)
                MyTask.time = timePicker.timeInMillis
            }

        }
    }

    private fun onSelectDateClickListener() {
        binding.content.selectDateTil.setOnClickListener {
            showDatePickerDialog(this) { date, calendar ->
                this.dateCalender.set(Calendar.YEAR, calendar.get(Calendar.YEAR))
                this.dateCalender.set(Calendar.MONTH, calendar.get(Calendar.MONTH))
                this.dateCalender.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH))
                this.dateCalender.set(Calendar.HOUR_OF_DAY, 0)
                this.dateCalender.set(Calendar.MINUTE, 0)
                this.dateCalender.set(Calendar.SECOND, 0)
                this.dateCalender.set(Calendar.MILLISECOND, 0)
                binding.content.selectDateTv.text = date
                MyTask.date = dateCalender.timeInMillis

            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun initViews() {
        val task = intent.parcelable<Task>(Constants.TASK_KEY) as Task
        binding.content.title.setText(task.title)
        binding.content.description.setText(task.description)
        val dateCalender = Calendar.getInstance()
        dateCalender.timeInMillis = task.date!!
        val year = dateCalender.get(Calendar.YEAR)
        val month = dateCalender.get(Calendar.MONTH)
        val day = dateCalender.get(Calendar.DAY_OF_MONTH)
        val timePicker = Calendar.getInstance()
        timePicker.timeInMillis = task.time!!
        val hour = timePicker.get(Calendar.HOUR_OF_DAY)
        val minutes= timePicker.get(Calendar.MINUTE)
        val minuteString = if (minutes == 0 ) "00" else minutes.toString()

        binding.content.selectDateTv.text = "$day/${month + 1}/$year"
        binding.content.selectTimeTv.text =  "${getHourIn12(hour)}:${minuteString} ${getTimeAmPm(hour)}"
    }
    private fun onSaveClick() {
        binding.content.btnSave.setOnClickListener {
            updateTask()
        }
    }

    override fun onStart() {
        super.onStart()
        Dao = TaskDataBase.getInstance(this).tasksDao()
    }
    private fun updateTask() {
        if (!isValid()) {
            return
        }
        val task = intent.parcelable<Task>(Constants.TASK_KEY) as Task
        task.title = binding.content.title.text.toString()
        task.description = binding.content.description.text.toString()
        task.date = dateCalender.timeInMillis
        task.time = timePicker.timeInMillis

        MyTask = task.copy(
            id = task.id,
            title = task.title,
            description = task.description,
            date = MyTask.date,
            time = MyTask.time,
            isDone = task.isDone
        )
        Dao.updateTask(MyTask)
        finish()

    }

    private fun isValid(): Boolean {
        var isValid = true
        if (binding.content.title.text.isNullOrBlank()) {
            isValid = false
            binding.content.titleTil.error = getString(R.string.add_task_title)
        } else {
            binding.content.titleTil.error = null
        }

        if (binding.content.selectDateTv.text.isNullOrBlank()) {
            isValid = false
            binding.content.selectDateTil.error = getString(R.string.add_task_date)
        } else {
            binding.content.selectDateTil.error = null
        }

        if (binding.content.selectTimeTv.text.isNullOrBlank()) {
            isValid = false
            binding.content.selectTimeTil.error = getString(R.string.add_task_time)
        } else {
            binding.content.selectTimeTil.error = null
        }

        return isValid
    }
}