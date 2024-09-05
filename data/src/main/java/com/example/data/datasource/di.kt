package com.example.data.datasource

import com.example.data.dataSourceContract.TasksOnlineDataSource
import com.example.data.db.TasksDao
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class di {
    @Binds
    abstract fun provideTasksOnlineDataSource(
        tasksOnlineDataSourceImpl: TasksOnlineDataSourceImpl
    ):TasksOnlineDataSource
}