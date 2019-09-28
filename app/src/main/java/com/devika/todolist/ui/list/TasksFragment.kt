package com.devika.todolist.ui.list

import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.devika.todolist.MyApplication
import com.devika.todolist.R
import com.devika.todolist.databinding.TasksFragBinding

class TasksFragment : Fragment() {
    lateinit var binding: TasksFragBinding
    lateinit var tasksViewModelFactory: TasksViewModelFactory
    lateinit var tasksViewModel: TasksViewModel
    lateinit var tasksAdapter: TasksAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.tasks_frag, container, false)
        setUPViewModel()
        initialixeUI()
        setHasOptionsMenu(true)
        binding.taskdListFltoatbtn.setOnClickListener {
            view!!.findNavController()
                .navigate(TasksFragmentDirections.tasksFragmentToAddTaskFragment())
        }
        tasksViewModel.taskList.observe(this, Observer {
            it?.let {
                tasksAdapter.updateTasks(it)
            }
        })
        tasksViewModel.taskDeails.observe(this, Observer {
            it?.let {
                view!!.findNavController().navigate(TasksFragmentDirections.actionTasksFragmentToTaskDetailsFragment(it))

            }
        })

        return binding.root

    }


    private fun initialixeUI() {
        tasksAdapter = TasksAdapter(emptyList(), MyApplication.tasksRepository, tasksViewModel)
        with(binding.recyclerView) {
            layoutManager = LinearLayoutManager(activity)
            adapter = tasksAdapter
        }
    }

    private fun setUPViewModel() {

        tasksViewModelFactory = TasksViewModelFactory(MyApplication.tasksRepository)
        tasksViewModel =
            ViewModelProviders.of(this, tasksViewModelFactory).get(TasksViewModel::class.java)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.option_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.delete_completed_task) {
            tasksViewModel.deleteSelectedTasks(tasksViewModel.taskList.value!!)
        }
        else  if (item.groupId == R.id.sortedTask) {
            if (!item.isChecked) {
                item.isChecked = true
                when (item.itemId) {
                    R.id.active -> tasksViewModel.getTasks(false)
                    R.id.completed -> tasksViewModel.getTasks(true)
                    else -> tasksViewModel.gettaskList()
                }
            }
        }
      return false
    }
}