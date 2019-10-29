package com.devika.todolist.ui.list

import androidx.lifecycle.*
import com.devika.todolist.model.Tasks
import com.devika.todolist.repository.TasksRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TasksViewModel(var repository: TasksRepository) : ViewModel() {

    var taskList = MutableLiveData<List<Tasks>>()
    var taskDeails = MutableLiveData<Tasks>()
    val empty: LiveData<Boolean> = Transformations.map(taskList) {
        it.isEmpty()
    }

    init {
        getTaskList()
    }

    fun getTaskList() {
        viewModelScope.launch {
            val tasksList = withContext(Dispatchers.IO) {
                repository.getTasks().asReversed()
            }
            withContext(Dispatchers.Main) {
                taskList.postValue(tasksList)
            }
        }
    }

    fun onclick(task: Tasks) {
        taskDeails.value = task
    }

    fun getTasks(isCompleted: Boolean) {
        viewModelScope.launch {
            val tasksList = withContext(Dispatchers.IO) {
                repository.getTasks(isCompleted).asReversed()
            }
            withContext(Dispatchers.Main) {
                taskList.postValue(tasksList)
            }
        }
    }

    fun deleteSelectedTasks(task: List<Tasks>) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                repository.deleteTasks(task)
            }
            getTaskList()
        }
    }


}