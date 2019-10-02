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
  // private val _empty=MutableLiveData<Boolean>()
    val empty: LiveData<Boolean> = Transformations.map(taskList){
      it.isEmpty()
  }
        //get() = _empty

    init {
        gettaskList()
    }

    fun gettaskList() {
        lateinit var tasksList: List<Tasks>
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                tasksList = repository.getTasks()
            }
            withContext(Dispatchers.Main) {
                taskList.postValue(tasksList.asReversed())
            }

        }
    }

    fun onclick(task: Tasks) {
        taskDeails.value = task
    }

    fun getTasks(task: Boolean) {
        lateinit var tasksList: List<Tasks>
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                tasksList = repository.getTasks(task)
            }
            withContext(Dispatchers.Main) {
                taskList.postValue(tasksList)
            }
        }
    }

    fun deleteSelectedTasks(task: List<Tasks>){
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                repository.deleteTasks(task)
            }
            gettaskList()
        }
    }


}