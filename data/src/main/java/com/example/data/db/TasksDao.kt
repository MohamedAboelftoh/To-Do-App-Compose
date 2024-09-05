package com.example.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.data.model.TaskDto
import com.example.domain.entity.Task
@Dao
interface TasksDao {
    @Insert
   suspend fun insertTask(task: TaskDto)
    @Delete
    suspend fun deleteTask(task: TaskDto)
    @Update
    suspend fun updateTask(task: TaskDto)
    @Query("SELECT * FROM task")
    suspend fun getAllTasks():List<TaskDto>
    @Query("SELECT * FROM task WHERE date = :date")
    suspend fun getTasksByDate(date : String): List<TaskDto>
}