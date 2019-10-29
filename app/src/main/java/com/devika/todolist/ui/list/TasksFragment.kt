package com.devika.todolist.ui.list

import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.devika.todolist.MyApplication
import com.devika.todolist.R
import com.devika.todolist.databinding.TasksFragBinding

class TasksFragment : Fragment() {
    lateinit var binding: TasksFragBinding
    lateinit var tasksViewModel: TasksViewModel
    lateinit var tasksAdapter: TasksAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.tasks_frag, container, false)
        initUI()
        setListener()
        setHasOptionsMenu(true)
        return binding.root
    }

    private fun initUI() {
        tasksViewModel =
            ViewModelProviders.of(this, TasksViewModelFactory(MyApplication.tasksRepository))
                .get(TasksViewModel::class.java)
        tasksAdapter = TasksAdapter(emptyList(), MyApplication.tasksRepository, tasksViewModel)
        with(binding.recyclerView) {
            layoutManager = LinearLayoutManager(activity)
            adapter = tasksAdapter
        }
    }

    private fun setListener() {
        binding.taskdListFltoatbtn.setOnClickListener {
            findNavController()
                .navigate(TasksFragmentDirections.tasksFragmentToAddTaskFragment())
        }
        tasksViewModel.taskList.observe(this, Observer {
            tasksAdapter.updateTasks(it)
        })
        tasksViewModel.taskDeails.observe(this, Observer {
            findNavController()
                .navigate(TasksFragmentDirections.tasksFragmentToEditTaskFragment(it))
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.option_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when {
            item.itemId == R.id.delete_completed_task -> tasksViewModel.deleteSelectedTasks(
                tasksViewModel.taskList.value!!
            )
            item.groupId == R.id.sortedTask -> if (!item.isChecked) {
                item.isChecked = true
                when (item.itemId) {
                    R.id.active -> tasksViewModel.getTasks(false)
                    R.id.completed -> tasksViewModel.getTasks(true)
                    else -> tasksViewModel.getTaskList()
                }
            }
        }
        return false
    }

}