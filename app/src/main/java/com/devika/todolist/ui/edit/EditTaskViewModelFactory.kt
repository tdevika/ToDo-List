package com.devika.todolist.ui.edit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.devika.todolist.model.Tasks
import com.devika.todolist.repository.TasksRepository
import java.lang.IllegalArgumentException

class EditTaskViewModelFactory(var editDetails:Tasks,var repository: TasksRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EditTaskViewModel::class.java)) {
            return EditTaskViewModel(editDetails,repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}