package com.devika.todolist.ui.addtask

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.devika.todolist.repository.TasksRepository
import java.lang.IllegalArgumentException

class AddTaskViewModelFactory(var repository: TasksRepository):ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(AddTaskViewModel::class.java)){
            return AddTaskViewModel(repository) as T
        }
        throw IllegalArgumentException("UnKnown ViewModel class")
    }
}