package com.example.domain.usecase

import com.example.domain.entity.Task
import com.example.domain.repo.TasksRepo
import javax.inject.Inject

class TasksUseCase @Inject constructor (
    private val tasksRepo: TasksRepo
) {
    suspend fun insertTask(task: Task){
        tasksRepo.insertTask(task)
    }
    suspend fun deleteTask(task: Task){
        tasksRepo.deleteTask(task)
    }
    suspend fun updateTask(task: Task){
        tasksRepo.updateTask(task)
    }
    suspend fun getTasksByDate(date : String):List<Task>{
        return tasksRepo.getTasksByDate(date)
    }
}