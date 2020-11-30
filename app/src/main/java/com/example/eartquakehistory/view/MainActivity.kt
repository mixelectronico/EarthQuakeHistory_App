package com.example.eartquakehistory.view

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.Window
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.eartquakehistory.R
import com.example.eartquakehistory.databinding.ActivityMainBinding
import com.example.eartquakehistory.model.EarthQuake
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.concurrent.fixedRateTimer

class MainActivity : AppCompatActivity() {

    private var listOfEvents = ArrayList<EarthQuake>()
    private lateinit var mViewModel : EventViewModel
    private lateinit var adapter : EventAdapter
    private lateinit var binding : ActivityMainBinding

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.top_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.aboutMenu -> {
                val aboutDialog = AboutDialogFragment()
                aboutDialog.show(supportFragmentManager, "AboutAPP")
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        //Iniciamos el VM
        mViewModel = ViewModelProvider(this).get(EventViewModel::class.java)

        //Iniciamos el canal de notificaciones.
        mViewModel.startNotificationChannel()

        //Iniciamos el adapter y se lo asignamos al recyclerview
        adapter = EventAdapter(listOfEvents)
        binding.eventListRecyclerview.adapter = adapter

        mViewModel.updateEventList()

        mViewModel.bringListOfEvents_ToView().observe(this,{
            adapter.updateData(it)
        })

        mViewModel.updateDatabasePeriodically()

    }

    override fun onResume() {
        super.onResume()
        mViewModel.saveLastViewedEvents()
    }
}