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

class EditTaskViewModel(editDetails: Tasks, var repository: TasksRepository) : ViewModel() {
    var taskEditedDetails = MutableLiveData<Tasks>()
    var isTaskEdited=MutableLiveData<Boolean>().apply { false }
    private val coroutineExceptionHandler= CoroutineExceptionHandler{_, exception -> onError(exception)}

            fun onError(exception:Throwable){
                isTaskEdited.postValue(false)
            }
    init {
        taskEditedDetails.postValue(editDetails)
    }

    fun updateTaskToDataBase() {
        viewModelScope.launch(coroutineExceptionHandler) {
            withContext(Dispatchers.IO) {
                repository.updateTasks(taskEditedDetails.value!!)
            }
            isTaskEdited.postValue(true)

        }
    }


}