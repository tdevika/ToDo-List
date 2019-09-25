package com.devika.todolist.ui.datails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.devika.todolist.repository.TasksRepository
import java.lang.IllegalArgumentException

class TaskDetailsViewModelFactory(var repository: TasksRepository):ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(TaskDetailsViewModel::class.java)){
            return TaskDetailsViewModel(repository) as T
        }
        throw IllegalArgumentException("UnKnown ViewModel class")
    }
}