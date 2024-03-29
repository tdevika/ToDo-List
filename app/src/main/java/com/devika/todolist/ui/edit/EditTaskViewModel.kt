package com.devika.todolist.ui.edit

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devika.todolist.model.Tasks
import com.devika.todolist.repository.TasksRepository
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class EditTaskViewModel(val task: Tasks, var repository: TasksRepository) : ViewModel() {
    var taskEditedDetails = MutableLiveData<Tasks>()
    var isTaskEdited=MutableLiveData<Boolean>(false)
    var isTaskDeleted = MutableLiveData<Boolean>(false)
    private val coroutineExceptionHandler= CoroutineExceptionHandler{_, exception -> onError(exception)}

            fun onError(exception:Throwable){
                isTaskEdited.postValue(false)
            }
    init {
        taskEditedDetails.postValue(task)
    }

    fun updateTaskToDataBase() {
        viewModelScope.launch(coroutineExceptionHandler) {
            withContext(Dispatchers.IO) {
                repository.updateTasks(taskEditedDetails.value!!)
            }
            isTaskEdited.postValue(true)

        }
    }
    fun deleteSelectedItem() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                repository.deleteTask(task)
            }
            isTaskDeleted.postValue(true)
        }
    }

}