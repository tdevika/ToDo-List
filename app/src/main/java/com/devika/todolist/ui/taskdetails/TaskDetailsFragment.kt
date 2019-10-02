package com.devika.todolist.ui.taskdetails

import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import com.devika.todolist.MyApplication
import com.devika.todolist.R
import com.devika.todolist.databinding.ItemDetailsBinding

class TaskDetailsFragment : Fragment() {
    lateinit var binding: ItemDetailsBinding
    lateinit var taskDetailsViewModel: TaskDetailsViewModel
    lateinit var taskDetailsViewModelFactory: TaskDetailsViewModelFactory
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate<ItemDetailsBinding>(
            inflater,
            R.layout.item_details,
            container,
            false
        )
        var task = TaskDetailsFragmentArgs.fromBundle(arguments!!).taskDetails
        taskDetailsViewModelFactory =
            TaskDetailsViewModelFactory(task, MyApplication.tasksRepository)
        taskDetailsViewModel = ViewModelProviders.of(this, taskDetailsViewModelFactory)
            .get(TaskDetailsViewModel::class.java)
        binding.taskdetail = taskDetailsViewModel
        binding.lifecycleOwner = this
        binding.itemDtailsFloatBtn.setOnClickListener {
            view!!.findNavController()
                .navigate(
                    TaskDetailsFragmentDirections.ActionTaskDetailsFragmentToEditTaskFragment(
                        task
                    )
                )
        }
        binding.checkBox.setOnCheckedChangeListener { button, isChecked ->
            if (isChecked) {
                taskDetailsViewModel.updateIsCompleted()

            }
        }
        isItemDeleted()
        setHasOptionsMenu(true)

        return binding.root
    }

    private fun isItemDeleted() {
        taskDetailsViewModel.isTaskDeleted.observe(this, Observer {
            if (it == true) {
                view!!.findNavController().navigate(TaskDetailsFragmentDirections.actionTaskDetailsFragmentToTasksFragment())
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.delete, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.delete) {
            taskDetailsViewModel.deleteSelectedItem(taskDetailsViewModel.task.value!!)
        }
        else if (item.itemId == android.R.id.home) {
            view!!.findNavController()
                .navigate(TaskDetailsFragmentDirections.actionTaskDetailsFragmentToTasksFragment())
            return true
        }
        return false
    }

}