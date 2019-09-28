package com.devika.todolist.ui.taskdetails

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devika.todolist.model.Tasks
import com.devika.todolist.repository.TasksRepository
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TaskDetailsViewModel(tasks: Tasks, var repository: TasksRepository) : ViewModel() {
    var task = MutableLiveData<Tasks>()
    var isTaskDeleted = MutableLiveData<Boolean>().apply { false }
    var coroutineExceptionHandler = CoroutineExceptionHandler { _, exception -> onError(exception) }

    private fun onError(exception: Throwable) {
        isTaskDeleted.postValue(false)
    }

    init {
        task.postValue(tasks)
    }

    fun updateIsCompleted() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                task.value!!.isCompleted = true
                task.value?.let { repository.updateTasks(it) }
            }
        }

    }

    fun deleteSelectedItem(task: Tasks) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {

                repository.deleteTask(task)

            }
            if (task.isCompleted){
            isTaskDeleted.postValue(true)
            }
        }
    }
}