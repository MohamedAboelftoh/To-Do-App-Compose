package com.example.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.data.model.TaskDto
import com.example.domain.entity.Task

@Database(entities = [TaskDto::class] , version = 1 , exportSchema = false )
abstract class AppDataBase : RoomDatabase() {
    abstract fun getTaskDao(): TasksDao
}