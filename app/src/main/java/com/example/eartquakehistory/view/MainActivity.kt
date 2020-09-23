package com.example.eartquakehistory.view

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
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

class MainActivity : AppCompatActivity(){

    private var listOfEvents = ArrayList<EarthQuake>()
    private lateinit var mViewModel : EventViewModel
    private lateinit var adapter : EventAdapter
//    private val CHANNEL_ID = "channel_id_example_01"
//    private val notificationId = 101

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Iniciamos el VM
        mViewModel = ViewModelProvider(this).get(EventViewModel::class.java)

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

//    private fun createNotificationChannel(){
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            val name = "EarthQuake Notification"
//            val descriptionText = "Event Database Notification"
//            val importance = NotificationManager.IMPORTANCE_DEFAULT
//            val channel = NotificationChannel(CHANNEL_ID,name,importance).apply {
//                description= descriptionText
//            }
//            val notificationManager: NotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
//            notificationManager.createNotificationChannel(channel)
//        }
//    }

//    fun activateNotification(lastEvent: EarthQuake){
//        if(mViewModel.checkLastDate()){
//            //Set the intent to create this activity when notification is pushed.
//            val intent = Intent(this, MainActivity::class.java).apply {
//                flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
//            }
//            val pendingIntent: PendingIntent = PendingIntent.getActivity(this, 0,intent,0)
//
//            val builder = NotificationCompat.Builder(this, CHANNEL_ID)
//                .setSmallIcon(R.drawable.ic_new_event)
//                .setContentTitle("Nuevos Eventos")
//                .setContentText("Tienes nuevos eventos registrados")
//                .setStyle(NotificationCompat.BigTextStyle().bigText("El ultimo evento registrado ocurrió "+
//                        lastEvent.RefGeografica +" con una magnitud de "+ lastEvent.Magnitud+ "a una profundidad de "+
//                        lastEvent.Profundidad))
//                .setContentIntent(pendingIntent)
//                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
//
//            with (NotificationManagerCompat.from(this)){
//                notify(notificationId, builder.build())
//            }
//        }
//    }
}