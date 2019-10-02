package com.devika.todolist.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.devika.todolist.model.Tasks

@Database(entities = [Tasks::class], version = 4, exportSchema = false)
abstract class TasksDatabase : RoomDatabase() {
    abstract fun tasksDao(): TasksDao

    companion object {
        @Volatile
        private var INSTANCE: TasksDatabase? = null

        fun getInstance(context: Context): TasksDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        TasksDatabase::class.java,
                        "task_list_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }


        }
    }
}