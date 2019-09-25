package com.devika.todolist.ui.itemdetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.devika.todolist.model.Tasks
import com.devika.todolist.repository.TasksRepository
import java.lang.IllegalArgumentException

class ItemDetailsViewModelFactory(var task:Tasks,var repository: TasksRepository):ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(ItemDetailsViewModel::class.java)){
            return ItemDetailsViewModel(task,repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}