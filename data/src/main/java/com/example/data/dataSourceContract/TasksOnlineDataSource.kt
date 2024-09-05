package com.example.data.dataSourceContract

import com.example.data.model.TaskDto
import com.example.domain.entity.Task

interface TasksOnlineDataSource {
     suspend fun getTasksFromDB():List<Task>
     suspend fun getTasksByDateFromDB(date : String):List<Task>
     suspend fun insertTaskToDB(task: TaskDto)
     suspend fun deleteTaskFromDB(task: TaskDto)
     suspend fun updateTask(task: TaskDto)
}