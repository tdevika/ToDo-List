package com.devika.todolist.database

import androidx.room.*
import com.devika.todolist.model.Tasks

@Dao
interface TasksDao {

    @Query("SELECT * FROM tasks ")
    suspend fun getTasks(): List<Tasks>

    @Insert
    suspend fun insertTasks(tasks: Tasks)

    @Update
    suspend fun updateTasks(tasks: Tasks)

    @Query("SELECT * FROM tasks where completed = :isCompleted")
    suspend fun getTasks(isCompleted: Boolean): List<Tasks>

    @Delete
    suspend fun deleteTasks(tasks:Tasks)


}