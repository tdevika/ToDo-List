package com.devika.todolist.ui.addtask

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devika.todolist.model.Tasks
import com.devika.todolist.repository.TasksRepository
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AddTaskViewModel(var taskRepository: TasksRepository) : ViewModel() {

    var isTaskAdded = MutableLiveData<Boolean>(false)


    private val coroutineExceptionHandler =
        CoroutineExceptionHandler { _, exception -> onError(exception) }

    private fun onError(exception: Throwable) {
        isTaskAdded.postValue(false)
    }

    fun setTaskToDatabase(
        title: String,
        description: String,
        alarm: String
    ) {
        viewModelScope.launch(coroutineExceptionHandler) {
            withContext(Dispatchers.IO) {
                taskRepository.setTasks(
                    Tasks(
                        title = title,
                        description = description,
                        alarm = alarm
                    )
                )
            }
            isTaskAdded.postValue(true)
        }

    }
}