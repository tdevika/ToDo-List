package com.devika.todolist.ui.itemdetails

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

class ItemDetailsFragment : Fragment() {
    lateinit var binding: ItemDetailsBinding
    lateinit var itemDetailsViewModel: ItemDetailsViewModel
    lateinit var itemDetailsViewModelFactory: ItemDetailsViewModelFactory
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
        var task = ItemDetailsFragmentArgs.fromBundle(arguments!!).taskDetails
        itemDetailsViewModelFactory =
            ItemDetailsViewModelFactory(task, MyApplication.tasksRepository)
        itemDetailsViewModel = ViewModelProviders.of(this, itemDetailsViewModelFactory)
            .get(ItemDetailsViewModel::class.java)
        binding.taskdetail = itemDetailsViewModel
        binding.lifecycleOwner = this
        binding.itemDtailsFloatBtn.setOnClickListener {
            view!!.findNavController()
                .navigate(
                    ItemDetailsFragmentDirections.ActionItemDetailsFragmentToEditTaskFragment(
                        task
                    )
                )
        }
        binding.checkBox.setOnCheckedChangeListener { button, isChecked ->
            if (isChecked) {
                itemDetailsViewModel.updateIsCompleted()

            }
        }
        isItemDeleted()
        setHasOptionsMenu(true)

        return binding.root
    }

    private fun isItemDeleted() {
        itemDetailsViewModel.isTaskDeleted.observe(this, Observer {
            if (it == true) {
                view!!.findNavController()
                    .navigate(ItemDetailsFragmentDirections.actionItemDetailsFragmentToTasksFragment())
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.delete_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.delete) {
            itemDetailsViewModel.deleteSelectedItem(itemDetailsViewModel.task.value!!)
        }
        return false
    }

}