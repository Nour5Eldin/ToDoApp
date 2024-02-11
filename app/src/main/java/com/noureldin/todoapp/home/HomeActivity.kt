package com.noureldin.todoapp.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import com.noureldin.todoapp.R
import com.noureldin.todoapp.databinding.ActivityHomeBinding
import com.noureldin.todoapp.fragment.AddTaskFragment
import com.noureldin.todoapp.fragment.SettingsFragment
import com.noureldin.todoapp.fragment.TaskFragment

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private var tasksFragment: TaskFragment? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initListeners()
        tasksFragment = TaskFragment()
        onAddTaskClick()


    }

    override fun onResume() {
        super.onResume()
        binding.bottomNavigation.selectedItemId = R.id.tasks
    }

    private fun onAddTaskClick() {
        binding.fabAddTask.setOnClickListener {
            showAddTaskBottomSheet()
        }
    }

    private fun showAddTaskBottomSheet() {
        val bottomSheet = AddTaskFragment()
        bottomSheet.onTaskAddedListener = AddTaskFragment.OnTaskAddedListener{Task ->
            tasksFragment!!.loadAllTasksOfDate(Task.date!!)

        }
        bottomSheet.show(supportFragmentManager, "")
    }

    fun  initListeners(){
       binding.bottomNavigation.setOnItemSelectedListener { menuItem ->
           if (menuItem.itemId == R.id.tasks) {
               showFragment(tasksFragment!!)
               binding.title.text = getString(R.string.to_do_list)
           } else if (menuItem.itemId == R.id.settings) {
               showFragment(SettingsFragment())
               binding.title.text = getString(R.string.settings)
               Log.e("HomeActivity/initListeners","${SettingsFragment()}")
           }
           return@setOnItemSelectedListener true
       }
    }

    private fun showFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
            .commit()
    }
}