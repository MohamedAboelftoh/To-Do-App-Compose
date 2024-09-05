package com.example.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.domain.entity.Task

@Entity(tableName = "task")
data class TaskDto(
    @PrimaryKey(autoGenerate = true)
    val id : Int ?= null,
    val title : String?= null,
    var isDone : Boolean = false,
    val time : String?= null ,
    val date : String?= null
){
    fun toTask():Task{
        return Task(id = id , title = title , isDone = isDone , time = time , date = date)
    }
}