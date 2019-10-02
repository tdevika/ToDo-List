package com.devika.todolist.ui.addtask

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Context
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
import java.util.*

class AddTaskFragment : Fragment() {
    lateinit var binding: TasksDetailsBinding
    lateinit var addTaskViewModel: AddTaskViewModel
    lateinit var addTaskViewModelFactory: AddTaskViewModelFactory
    val calender = Calendar.getInstance()
    val year = calender.get(Calendar.YEAR)
    val month = calender.get(Calendar.MONTH)
    val day = calender.get(Calendar.DAY_OF_MONTH)


    @SuppressLint("SetTextI18n")
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
       var dpd:DatePickerDialog
        addTaskViewModelFactory = AddTaskViewModelFactory(MyApplication.tasksRepository)
        addTaskViewModel = ViewModelProviders.of(this, addTaskViewModelFactory)
            .get(AddTaskViewModel::class.java)
        binding.addTaskViewModel = addTaskViewModel
        binding.lifecycleOwner = this
        binding.datePicBtn.setOnClickListener {
            dpd = DatePickerDialog(view!!.context, DatePickerDialog.OnDateSetListener { view, year, month, day ->
                binding.datePicEditText.setText("" + day + "/" + month + "/" + year)
            }, year, month, day)
            dpd.show()

        }
    }
}