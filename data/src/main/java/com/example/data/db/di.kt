package com.example.data.db

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object di {
    @Provides
    fun provideDataBase(
        @ApplicationContext context: Context
    ): AppDataBase {
        return Room.databaseBuilder(context , AppDataBase::class.java , "tasks_db")
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()
    }
    @Provides
    fun provideTasksDao(appDataBase: AppDataBase): TasksDao {
        return appDataBase.getTaskDao()
    }
}