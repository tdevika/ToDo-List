package com.devika.todolist.ui.addtask

import android.app.AlarmManager
import android.app.DatePickerDialog
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
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
import com.devika.todolist.databinding.AddTaskBinding
import com.devika.todolist.ui.addtask.NotificationReceiver.Companion.TASK
import java.text.SimpleDateFormat
import java.util.*


class AddTaskFragment : Fragment() {

    private lateinit var alarmDate: Date
    lateinit var binding: AddTaskBinding
    lateinit var viewModel: AddTaskViewModel
    private val calendar = Calendar.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.add_task, container, false)
        setupViewModel()
        setClickListener()
        return binding.root
    }

    private fun setClickListener() {
        binding.alarmEt.setOnClickListener { showDatePicker() }
        binding.taskDtailsFloatBtn.setOnClickListener { addTask() }
    }


    private fun setupViewModel() {
        viewModel =
            ViewModelProviders.of(this, AddTaskViewModelFactory(MyApplication.tasksRepository))
                .get(AddTaskViewModel::class.java)
        viewModel.isTaskAdded.observe(this, Observer {
            if (it == true) {
                findNavController().navigate(AddTaskFragmentDirections.addTaskFragmentToTasksFragment())
                Toast.makeText(context, "Tasks is Added", Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun showDatePicker() {
        val dateListener = DatePickerDialog.OnDateSetListener { datePicker, year, month, day ->
            calendar.set(Calendar.DAY_OF_MONTH, day)
            calendar.set(Calendar.MONTH, month)
            calendar.set(Calendar.YEAR, year)
            showTimePicker()
        }
        context?.let {
            DatePickerDialog(
                it,
                dateListener,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }
    }

    private fun addTask() {
        if (binding.taskTitle.text.isBlank()) {
            Toast.makeText(context, "Please Enter Title", Toast.LENGTH_SHORT).show()
        } else {
            if (::alarmDate.isInitialized) {
                setAlarm()
            }
            viewModel.setTaskToDatabase(
                binding.taskTitle.text.toString(),
                binding.desciption.text.toString(),
                binding.alarmEt.text.toString()
            )
        }
    }

    private fun showTimePicker() {
        val timeListener = TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
            calendar.set(Calendar.HOUR_OF_DAY, hour)
            calendar.set(Calendar.MINUTE, minute)
            alarmDate = calendar.time
            binding.alarmEt.setText(SimpleDateFormat("dd/MM/yyyy   HH:mm").format(alarmDate))
        }
        TimePickerDialog(
            context,
            timeListener,
            calendar.get(Calendar.HOUR_OF_DAY),
            calendar.get(Calendar.MINUTE),
            true
        ).show()
    }

    private fun setAlarm() {
        val intent = Intent(context, NotificationReceiver::class.java)
        intent.putExtra(TASK, binding.taskTitle.text.toString())
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            1,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )
        val alarmManager = context!!.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.set(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            pendingIntent
        )
    }
}