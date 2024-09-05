package com.example.domain.repo

import com.example.domain.entity.Task

interface TasksRepo {
     suspend fun getTasks():List<Task>
     suspend fun getTasksByDate(date : String):List<Task>
     suspend fun insertTask(task: Task)
     suspend fun deleteTask(task: Task)
     suspend fun updateTask(task: Task)
}