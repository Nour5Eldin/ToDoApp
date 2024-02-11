package com.noureldin.todoapp.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
data class Task(
    @PrimaryKey(autoGenerate = true)
    val id: Int? =null,
    var title: String?=null,
    var description: String? =null,
    var date: Long? = null,
    var time: Long? =null,
    var isDone: Boolean = false
) :Parcelable
