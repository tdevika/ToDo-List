package com.devika.todolist.ui.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.devika.todolist.repository.TasksRepository
import com.devika.todolist.ui.datails.TaskDetailsViewModel
import java.lang.IllegalArgumentException

class TasksViewModelFactory(var repository: TasksRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(TasksViewModel::class.java)){
            return TasksViewModel(repository) as T
        }
        throw IllegalArgumentException("UnKnown ViewModel class")
    }
}