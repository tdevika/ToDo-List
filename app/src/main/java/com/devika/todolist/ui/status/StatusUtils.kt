package com.devika.todolist.ui.status

import com.devika.todolist.model.Tasks

fun getActiveAndCompletedTask(tasks:List<Tasks>?):StatusResults{
    return if (tasks == null||tasks.isEmpty()){
        StatusResults(0f,0f)
    }else{
        val totalTasks=tasks.size
        val noOfActiveTasks=tasks.count { it.isActive }
        StatusResults(
            activeTaskPercent = 100f * noOfActiveTasks /tasks.size,
            completedTaskPercent = 100f * (totalTasks-noOfActiveTasks)/tasks.size

        )
    }

}

data class StatusResults(val activeTaskPercent:Float,val completedTaskPercent:Float)