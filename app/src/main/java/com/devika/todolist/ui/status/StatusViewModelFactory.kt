package com.devika.todolist.ui.status

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.devika.todolist.repository.TasksRepository

class StatusViewModelFactory(var repository: TasksRepository):ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(StatusViewModel::class.java)){
            return StatusViewModel(repository) as T

        }
        throw IllegalArgumentException("Unknown ViewModel")
    }
}