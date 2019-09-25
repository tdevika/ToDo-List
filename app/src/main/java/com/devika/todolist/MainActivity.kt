package com.devika.todolist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.ui.*
import androidx.navigation.ui.NavigationUI.setupActionBarWithNavController
import com.devika.todolist.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var appBarConfiguration: AppBarConfiguration
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setSupportActionBar(binding.toolbar)
        setNavigationView()
    }

    private fun setNavigationView() {
        val navController = findNavController(R.id.myNavHost)
        appBarConfiguration = AppBarConfiguration.Builder(R.id.tasksFragment, R.id.statusFragment)
            .setDrawerLayout(binding.drawerLayout)
            .build()
        setupActionBarWithNavController(navController, appBarConfiguration)
        findViewById<NavigationView>(R.id.navView).setupWithNavController(navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        return findNavController(R.id.myNavHost).navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}
