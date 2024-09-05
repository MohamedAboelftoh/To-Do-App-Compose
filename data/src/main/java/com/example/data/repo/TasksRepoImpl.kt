package com.example.data.repo

import com.example.data.dataSourceContract.TasksOnlineDataSource
import com.example.data.model.TaskDto
import com.example.domain.entity.Task
import com.example.domain.repo.TasksRepo
import javax.inject.Inject

class TasksRepoImpl @Inject constructor(
    private val tasksOnlineDataSource: TasksOnlineDataSource
) : TasksRepo {
    override suspend fun getTasks(): List<Task> {
        return tasksOnlineDataSource.getTasksFromDB()
    }

    override suspend fun getTasksByDate(date: String): List<Task> {
        return tasksOnlineDataSource.getTasksByDateFromDB(date)
    }

    override suspend fun insertTask(task: Task) {
        val t = TaskDto(id = task.id , title = task.title , isDone = task.isDone , time = task.time , date = task.date)
        tasksOnlineDataSource.insertTaskToDB(t)
    }

    override suspend fun deleteTask(task: Task) {
        val t = TaskDto(id = task.id , title = task.title , isDone = task.isDone , time = task.time , date = task.date)
        tasksOnlineDataSource.deleteTaskFromDB(t)
    }

    override suspend fun updateTask(task: Task) {
        val t = TaskDto(id = task.id , title = task.title , isDone = task.isDone , time = task.time , date = task.date)
        tasksOnlineDataSource.updateTask(t)
    }
}