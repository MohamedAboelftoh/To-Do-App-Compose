package com.example.data.repo

import com.example.domain.repo.TasksRepo
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class di {
    @Binds
    abstract fun provideTasksRepo(
        tasksRepoImpl: TasksRepoImpl
    ):TasksRepo
}