package com.example.todocompose.ui.viewmodels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.entity.Task
import com.example.domain.usecase.TasksUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class ButtonSheetViewModel @Inject constructor (
   private val tasksUseCase: TasksUseCase
) : ViewModel() {
    fun insertTask(task: Task){
        viewModelScope.launch {
            tasksUseCase.insertTask(task)
        }
    }
}