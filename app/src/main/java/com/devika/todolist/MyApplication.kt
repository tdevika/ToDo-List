package com.devika.todolist

import android.app.Application
import com.devika.todolist.repository.TasksRepository

class MyApplication:Application() {
    companion object{lateinit var tasksRepository: TasksRepository}

    override fun onCreate() {
        super.onCreate()
        tasksRepository=TasksRepository(applicationContext)
    }
}