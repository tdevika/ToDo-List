package com.devika.todolist.ui.taskdetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.devika.todolist.model.Tasks
import com.devika.todolist.repository.TasksRepository
import java.lang.IllegalArgumentException

class TaskDetailsViewModelFactory(var task:Tasks, var repository: TasksRepository):ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(TaskDetailsViewModel::class.java)){
            @Suppress("UNCHECKED_CAST")
            return TaskDetailsViewModel(task,repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}