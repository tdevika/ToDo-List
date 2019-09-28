package com.devika.todolist.ui.edit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.devika.todolist.MyApplication
import com.devika.todolist.R
import com.devika.todolist.databinding.EditTaskBinding

class EditTaskFragment : Fragment() {
    lateinit var binding: EditTaskBinding
    lateinit var editTaskViewModel: EditTaskViewModel
    lateinit var editTaskViewModelFactory: EditTaskViewModelFactory
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.edit_task, container, false)
        var editDetails=EditTaskFragmentArgs.fromBundle(arguments!!).editDetails
        editTaskViewModelFactory=EditTaskViewModelFactory(editDetails,MyApplication.tasksRepository)
        editTaskViewModel=ViewModelProviders.of(this,editTaskViewModelFactory).get(EditTaskViewModel::class.java)
       binding.lifecycleOwner=this
        binding.editTask=editTaskViewModel
        editTaskViewModel.isTaskEdited.observe(this, Observer {
            if(it==true){
                findNavController().navigate(EditTaskFragmentDirections.actionEditTaskFragmentToTaskDetailsFragment(editTaskViewModel.taskEditedDetails.value!!))
            }
        })


        return binding.root
    }
}