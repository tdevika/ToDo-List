package com.devika.todolist.ui.status

import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.devika.todolist.MyApplication
import com.devika.todolist.R
import com.devika.todolist.databinding.StatusFragBinding

class StatusFragment: Fragment() {
    lateinit var binding:StatusFragBinding
    lateinit var statusViewModel:StatusViewModel
    lateinit var statusViewModelFactory: StatusViewModelFactory
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
         binding=DataBindingUtil.inflate(inflater, R.layout.status_frag,container,false)
        setHasOptionsMenu(true)
        setUpViewModel()
        binding.status=statusViewModel
        binding.lifecycleOwner=this

    return binding.root
    }

    private fun setUpViewModel() {
        statusViewModelFactory= StatusViewModelFactory(MyApplication.tasksRepository)
        statusViewModel=ViewModelProviders.of(this,statusViewModelFactory).get(StatusViewModel::class.java)

    }


}