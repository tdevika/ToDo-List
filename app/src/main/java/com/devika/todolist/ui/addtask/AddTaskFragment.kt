package com.devika.todolist.ui.addtask

import android.app.AlarmManager
import android.app.DatePickerDialog
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
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
import com.devika.todolist.ui.addtask.NotificationReceiver.Companion.TASK
import java.text.SimpleDateFormat
import java.util.*

class AddTaskFragment : Fragment() {

    lateinit var alarmDate: Date
    lateinit var binding: TasksDetailsBinding
    lateinit var addTaskViewModel: AddTaskViewModel
    lateinit var addTaskViewModelFactory: AddTaskViewModelFactory
    lateinit var alarmManager: AlarmManager
    lateinit var pendingIntent: PendingIntent
    var timeSetListener: TimePickerDialog.OnTimeSetListener? = null
    var dateSetListener: DatePickerDialog.OnDateSetListener? = null
    private val calendar = Calendar.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.tasks_details, container, false)
        setupViewModel()
        binding.alarmBtn.setOnClickListener {
            showDatePicker()
        }
        binding.taskDtailsFloatBtn.setOnClickListener {
            if (::alarmDate.isInitialized) {
                val intent = Intent(context, NotificationReceiver::class.java)
                intent.putExtra(TASK, addTaskViewModel.title.value)
                pendingIntent =
                    PendingIntent.getBroadcast(
                        context,
                        1,
                        intent,
                        PendingIntent.FLAG_UPDATE_CURRENT
                    )
                alarmManager.set(
                    AlarmManager.RTC_WAKEUP,
                    calendar.timeInMillis,
                    pendingIntent
                )
            }
            addTaskViewModel.setTaskToDatabase()
        }
        setNotification()
        return binding.root
    }

    private fun setupViewModel() {
        addTaskViewModelFactory = AddTaskViewModelFactory(MyApplication.tasksRepository)
        addTaskViewModel = ViewModelProviders.of(this, addTaskViewModelFactory)
            .get(AddTaskViewModel::class.java)
        binding.detaillViewModel = addTaskViewModel
        binding.lifecycleOwner = this
        addTaskViewModel.isTaskAdded.observe(this, Observer {
            if (it == true) {
                findNavController().navigate(AddTaskFragmentDirections.addTaskFragmentToTasksFragment())
                Toast.makeText(context, "Tasks is Added", Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun setNotification() {
        val formatter = SimpleDateFormat("dd/MM/yyyy   HH:mm")
        alarmManager = context!!.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        dateSetListener = DatePickerDialog.OnDateSetListener { datePicker, year, month, day ->
            calendar.set(Calendar.DAY_OF_MONTH, day)
            calendar.set(Calendar.MONTH, month)
            calendar.set(Calendar.YEAR, year)
            showTimePicker()
        }
        timeSetListener = TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
            calendar.set(Calendar.HOUR_OF_DAY, hour)
            calendar.set(Calendar.MINUTE, minute)
            alarmDate=calendar.time
            binding.alarmEt.setText(formatter.format(alarmDate))
        }
    }

    private fun showDatePicker() {
        context?.let {
            DatePickerDialog(
                it,
                dateSetListener,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }
    }

    fun showTimePicker() {
        TimePickerDialog(
            context,
            timeSetListener,
            calendar.get(Calendar.HOUR_OF_DAY),
            calendar.get(Calendar.MINUTE),
            true
        ).show()
    }
}