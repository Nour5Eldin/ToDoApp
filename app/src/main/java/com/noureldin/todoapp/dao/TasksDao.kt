package com.noureldin.todoapp.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.noureldin.todoapp.model.Task

@Dao
interface TasksDao {
    @Insert
    fun insertTask(task: Task)

    @Delete
    fun deleteTask(task: Task)

    @Update
    fun updateTask(task: Task)

    @Query("select * from Task")
    fun getAllTasks() : List<Task>

    @Query("select * from Task where date = :date")
    fun getTaskByDate(date:Long): List<Task>
}