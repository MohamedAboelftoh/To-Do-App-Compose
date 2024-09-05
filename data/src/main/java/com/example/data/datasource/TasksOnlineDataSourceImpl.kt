package com.example.data.datasource

import com.example.data.dataSourceContract.TasksOnlineDataSource
import com.example.data.db.TasksDao
import com.example.data.model.TaskDto
import com.example.domain.entity.Task
import javax.inject.Inject

class TasksOnlineDataSourceImpl @Inject constructor (
    private val tasksDao: TasksDao
):TasksOnlineDataSource {
    override suspend fun getTasksFromDB(): List<Task> {
        return tasksDao.getAllTasks().map {
            it.toTask()
        }
    }

    override suspend fun getTasksByDateFromDB(date: String): List<Task> {
        return tasksDao.getTasksByDate(date).map { it.toTask() }
    }


    override suspend fun insertTaskToDB(task: TaskDto) {
        tasksDao.insertTask(task)
    }

    override suspend fun deleteTaskFromDB(task: TaskDto) {
        tasksDao.deleteTask(task)
    }

    override suspend fun updateTask(task: TaskDto) {
        tasksDao.updateTask(task)
    }
}