package com.devika.todolist.ui.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.devika.todolist.R
import com.devika.todolist.databinding.TasksListBinding
import com.devika.todolist.model.Tasks
import com.devika.todolist.repository.TasksRepository

class TasksAdapter(
    var tasks: List<Tasks>,
    var tasksRepository: TasksRepository,
    val tasksViewModel: TasksViewModel
) : RecyclerView.Adapter<TasksAdapter.TaskViewHolder>() {

    fun updateTasks(tasks: List<Tasks>) {
        this.tasks = tasks
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val container = LayoutInflater.from(parent.context)
        return TaskViewHolder(TasksListBinding.inflate(container, parent, false))
    }

    override fun getItemCount(): Int {
        return tasks.size
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = tasks[position]
        holder.setTasks(task)
    }

    inner class TaskViewHolder(var binding: TasksListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun setTasks(task: Tasks) {

            binding.viewModel = task
            binding.checkBox.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    task.isCompleted = true
                    tasksRepository.updateTasks(task)
                } else {
                    task.isCompleted = false
                }
            }
            binding.root.setOnClickListener {
                tasksViewModel.onclick(task)
            }
        }
    }
}