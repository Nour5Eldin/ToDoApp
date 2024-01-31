package com.noureldin.todoapp.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.noureldin.todoapp.R
import com.noureldin.todoapp.databinding.ActivityHomeBinding
import com.noureldin.todoapp.fragment.SettingsFragment
import com.noureldin.todoapp.fragment.TaskFragment

class HomeActivity : AppCompatActivity() {
    lateinit var binding: ActivityHomeBinding
    private var taskFragment: TaskFragment? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        taskFragment = TaskFragment()


        binding.bottomNavigation.setOnItemSelectedListener { menuItem ->
            if (menuItem.itemId == R.id.tasks) {
                showFragment(taskFragment!!)
                binding.title.text = getString(R.string.to_do_list)

            } else if (menuItem.itemId == R.id.settings) {
                showFragment(SettingsFragment())
                binding.title.text = getString(R.string.settings)
            }
            true
        }

    }

    private fun showFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
            .commit()
    }
}