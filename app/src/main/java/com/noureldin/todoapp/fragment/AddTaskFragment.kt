package com.noureldin.todoapp.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.noureldin.todoapp.R
import com.noureldin.todoapp.dao.TasksDao
import com.noureldin.todoapp.databinding.FragmentAddTaskBinding
import com.noureldin.todoapp.db.TaskDataBase
import com.noureldin.todoapp.model.Task
import com.noureldin.todoapp.utils.getHourIn12
import com.noureldin.todoapp.utils.getTimeAmPm
import com.noureldin.todoapp.utils.showDatePickerDialog
import com.noureldin.todoapp.utils.showTimePickerDialog
import java.util.Calendar

class AddTaskFragment : Fragment() {
    lateinit var binding: FragmentAddTaskBinding
    lateinit var dao: TasksDao
    private var dateCalender = Calendar.getInstance()
    private var timePicker = Calendar.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding= FragmentAddTaskBinding.inflate(layoutInflater)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onSelectedDateClickListener()
        onSelectedTimeClickListener()
        onAddTakClickListener()
    }

    private fun onAddTakClickListener() {
        binding.addTaskBtn.setOnClickListener {
            CreateNewTask()
        }
    }

    private fun CreateNewTask() {
        if (!isValid()){
            return
        }
        val task = Task(
            title = binding.title.text.toString().trim(),
            description = binding.description.text.toString().trim(),
            date = dateCalender.timeInMillis,
            time = timePicker.timeInMillis
        )
        dao.insertTask(task)
        onTaskAddedListener?.onTaskAdded(task)


    }

    override fun onStart() {
        super.onStart()
        dao = TaskDataBase.getInstance(requireContext()).tasksDao()
    }
    private fun isValid(): Boolean {
        var  isValid = true
        if (binding.title.text.isNullOrBlank()){
            isValid = false
            binding.titleTil.error = getString(R.string.add_task_title)
        }else {
            binding.titleTil.error = null
        }
        if (binding.selectDateTv.text.isNullOrBlank()){
            isValid = false
            binding.selectDateTil.error = getString(R.string.add_task_date)
        }else {
            binding.selectDateTil.error = null
        }
        if (binding.selectTimeTv.text.isNullOrBlank()){
            isValid = false
            binding.selectTimeTil.error = getString(R.string.add_task_time)
        }else {
            binding.selectTimeTil.error = null
        }
        return isValid
    }

    private fun onSelectedTimeClickListener() {
        binding.selectTimeTil.setOnClickListener {
            val calendar = Calendar.getInstance()
            showTimePickerDialog(calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), title = "Select your Task Timer", childFragmentManager){
                hour , minutes ->
                val minuteString = if (minutes ==0) "00" else minutes.toString()
                binding.selectTimeTv.text = "${getHourIn12(hour)}: ${minuteString} ${getTimeAmPm(hour)}"
                this.timePicker.set(Calendar.YEAR ,0)
                this.timePicker.set(Calendar.MONTH ,0)
                this.timePicker.set(Calendar.DAY_OF_MONTH ,0)
                this.timePicker.set(Calendar.HOUR_OF_DAY , hour)
                this.timePicker.set(Calendar.MINUTE ,minutes)
                this.timePicker.set(Calendar.SECOND ,0)
                this.timePicker.set(Calendar.MILLISECOND ,0)
            }
        }
    }

    private fun onSelectedDateClickListener() {
        binding.selectDateTil.setOnClickListener{
            context?.let {context->
                showDatePickerDialog(context){date , calender ->
                    this.dateCalender.set(Calendar.YEAR, calender.get(Calendar.YEAR))
                    this.dateCalender.set(Calendar.MONTH, calender.get(Calendar.MONTH))
                    this.dateCalender.set(Calendar.DAY_OF_MONTH, calender.get(Calendar.DAY_OF_MONTH))
                    this.dateCalender.set(Calendar.HOUR_OF_DAY,0)
                    this.dateCalender.set(Calendar.MINUTE,0)
                    this.dateCalender.set(Calendar.SECOND,0)
                    this.dateCalender.set(Calendar.MILLISECOND,0)
                    binding.selectDateTv.text = date
                }
            }
        }
    }

    var onTaskAddedListener: OnTaskAddedListener? =null

    fun interface OnTaskAddedListener {
        fun  onTaskAdded( task: Task)
    }
}