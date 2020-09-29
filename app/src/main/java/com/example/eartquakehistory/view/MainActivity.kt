package com.example.eartquakehistory.view

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.ViewModelProvider
import com.example.eartquakehistory.EventViewModel
import com.example.eartquakehistory.R
import com.example.eartquakehistory.model.EarthQuake
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var listOfEvents = ArrayList<EarthQuake>()
    private lateinit var mViewModel : EventViewModel
    private lateinit var adapter : EventAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Iniciamos el VM
        mViewModel = ViewModelProvider(this).get(EventViewModel::class.java)

        //Iniciamos el canal de notificaciones.
        mViewModel.startNotificationChannel()

        //Iniciamos el adapter y se lo asignamos al recyclerview
        adapter = EventAdapter(listOfEvents)
        event_list_recyclerview.adapter = adapter

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