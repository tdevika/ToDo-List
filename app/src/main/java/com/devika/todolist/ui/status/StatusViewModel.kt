package com.devika.todolist.ui.status

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devika.todolist.model.Tasks
import com.devika.todolist.repository.TasksRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class StatusViewModel(var repository: TasksRepository) : ViewModel() {
    private val _activeTasksPercent = MutableLiveData<Float>()
    val activeTasksPercent: LiveData<Float>
        get() = _activeTasksPercent
    private val _completedTasksPercent = MutableLiveData<Float>()
    val completedTasksPercent: LiveData<Float>
        get() = _completedTasksPercent
    private val _empty = MutableLiveData<Boolean>()
    val empty: LiveData<Boolean>
        get() = _empty


    init {
        getTasksStatus()
    }

    private fun getTasksStatus() {
        lateinit var statusTasks: List<Tasks>
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                statusTasks = repository.getTasks()
            }
            withContext(Dispatchers.Main) {
                setTasksStatus(statusTasks)


            }
        }

    }

    private fun setTasksStatus(statusTasks: List<Tasks>) {
        getActiveAndCompletedTask(statusTasks).let {
            _activeTasksPercent.value = it.activeTaskPercent
            _completedTasksPercent.value = it.completedTaskPercent
        }
        _empty.value = statusTasks.isNullOrEmpty()

    }

}