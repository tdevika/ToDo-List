package com.devika.todolist.ui.datails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation.findNavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.devika.todolist.MyApplication
import com.devika.todolist.R
import com.devika.todolist.databinding.TasksDetailsBinding
import com.devika.todolist.databinding.TasksFragBinding
import com.devika.todolist.repository.TasksRepository
import com.devika.todolist.ui.list.TasksFragmentDirections

class TaskDetailsFragment : Fragment() {
    lateinit var binding: TasksDetailsBinding
    lateinit var taskDetailsViewModel: TaskDetailsViewModel
    lateinit var taskDetailsViewModelFactory: TaskDetailsViewModelFactory


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.tasks_details, container, false)
        setupViewModel()
        taskDetailsViewModel.isTaskAdded.observe(this, Observer {
            if (it==true){
                   findNavController().navigate(TaskDetailsFragmentDirections.taskDetailsFragmentToTasksFragment())
                    Toast.makeText(context,"Tasks is Added",Toast.LENGTH_LONG).show()
            }
        })
        return binding.root

    }

    private fun setupViewModel() {
        taskDetailsViewModelFactory = TaskDetailsViewModelFactory(MyApplication.tasksRepository)
        taskDetailsViewModel = ViewModelProviders.of(this, taskDetailsViewModelFactory)
            .get(TaskDetailsViewModel::class.java)
        binding.detaillViewModel = taskDetailsViewModel
        binding.lifecycleOwner = this
    }
}