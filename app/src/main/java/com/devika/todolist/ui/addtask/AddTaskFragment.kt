package com.devika.todolist.ui.addtask

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.devika.todolist.MyApplication
import com.devika.todolist.R
import com.devika.todolist.databinding.TasksDetailsBinding

class AddTaskFragment : Fragment() {
    lateinit var binding: TasksDetailsBinding
    lateinit var addTaskViewModel: AddTaskViewModel
    lateinit var addTaskViewModelFactory: AddTaskViewModelFactory


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.tasks_details, container, false)
        setupViewModel()
        addTaskViewModel.isTaskAdded.observe(this, Observer {
            if (it==true){
                   findNavController().navigate(AddTaskFragmentDirections.addTaskFragmentToTasksFragment())
                    Toast.makeText(context,"Tasks is Added",Toast.LENGTH_LONG).show()
            }
        })
        return binding.root

    }

    private fun setupViewModel() {
        addTaskViewModelFactory = AddTaskViewModelFactory(MyApplication.tasksRepository)
        addTaskViewModel = ViewModelProviders.of(this, addTaskViewModelFactory)
            .get(AddTaskViewModel::class.java)
        binding.detaillViewModel = addTaskViewModel
        binding.lifecycleOwner = this
    }
}