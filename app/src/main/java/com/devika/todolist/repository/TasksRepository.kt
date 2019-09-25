package com.devika.todolist.repository

import android.content.Context
import com.devika.todolist.database.TasksDatabase
import com.devika.todolist.model.Tasks
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TasksRepository(context: Context) {
    var taskDao = TasksDatabase.getInstance(context).tasksDao()

    suspend fun getTasks(): List<Tasks> {
        return taskDao.getTasks()
    }

    suspend fun setTasks(tasks: Tasks) {
        taskDao.insertTasks(tasks)
    }

    fun updateTasks(tasks: Tasks) {
        CoroutineScope(Dispatchers.IO).launch {
            taskDao.updateTasks(tasks)
        }
    }

    suspend fun getTasks(isCompleted: Boolean): List<Tasks> {
        return taskDao.getTasks(isCompleted)
    }

   suspend fun deleteTasks(tasks: List<Tasks>) {
            for (task in tasks) {
                if (task.isCompleted) {
                    taskDao.deleteTasks(task)
                }
            }
        }

   suspend fun deleteTask(task: Tasks) {
       if (task.isCompleted) {
       taskDao.deleteTasks(task)
       }
    }
}