package com.example.todocompose.ui.viewmodels
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Update
import com.example.domain.entity.Task
import com.example.domain.usecase.TasksUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class HomeViewModel @Inject constructor (
    private val tasksUseCase: TasksUseCase
) : ViewModel() {
    val tasks = mutableStateOf<List<Task>>(listOf())
    fun getTasksByDate(date : String){
        viewModelScope.launch {
            tasks.value = tasksUseCase.getTasksByDate(date)
        }
    }
    fun deleteTask(task: Task){
        viewModelScope.launch {
            tasksUseCase.deleteTask(task)
        }
    }

    fun updateTask(task :Task){
        viewModelScope.launch {
            tasksUseCase.updateTask(task)
        }
    }

}