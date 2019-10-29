package com.devika.todolist.ui.edit

import android.os.Bundle
import android.view.*
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
    lateinit var viewModel: EditTaskViewModel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.edit_task, container, false)
        setHasOptionsMenu(true)
        viewModel =
            ViewModelProviders.of(
                this,
                EditTaskViewModelFactory(
                    EditTaskFragmentArgs.fromBundle(arguments!!).task,
                    MyApplication.tasksRepository
                )
            ).get(EditTaskViewModel::class.java)
        binding.lifecycleOwner = this
        binding.editTask = viewModel
        viewModel.isTaskEdited.observe(this, Observer {
            if (it == true) {
                findNavController().navigate(
                    EditTaskFragmentDirections.actionEditTaskFragmentToTasksFragment()
                )
            }
        })
        viewModel.isTaskDeleted.observe(this, Observer {
            if (it == true) {
                findNavController()
                    .navigate(EditTaskFragmentDirections.actionEditTaskFragmentToTasksFragment())
            }
        })
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.delete, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.delete) {
            viewModel.deleteSelectedItem()
        } else if (item.itemId == android.R.id.home) {
            findNavController()
                .navigate(EditTaskFragmentDirections.actionEditTaskFragmentToTasksFragment())
            return true
        }
        return false
    }
}