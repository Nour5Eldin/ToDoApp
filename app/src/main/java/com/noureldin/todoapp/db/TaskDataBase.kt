package com.noureldin.todoapp.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.noureldin.todoapp.dao.TasksDao
import com.noureldin.todoapp.model.Task

@Database(entities = [Task::class], version = 1, exportSchema = true)
abstract class TaskDataBase: RoomDatabase() {
    abstract fun tasksDao(): TasksDao

    companion object{
        private var instance: TaskDataBase? = null

        fun getInstance(context: Context): TaskDataBase{
            if (instance == null){
                instance = Room.databaseBuilder(context,TaskDataBase::class.java, "TaskDB")
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build()
            }
            return instance!!
        }
    }
}