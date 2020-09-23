package com.example.eartquakehistory.model

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.eartquakehistory.R
import com.example.eartquakehistory.view.MainActivity

class UpdateWorker(context: Context, params: WorkerParameters): Worker(context, params) {

    private var mContext = context
    val repository = EventRepository(mContext)
    private val listOfEvents : LiveData<List<EarthQuake>> = repository.bringListOfEventsToViewModel()
    private val CHANNEL_ID = "channel_id_example_01"
    private val notificationId = 101

    override fun doWork(): Result {
        repository.getEventList()

//        if(repository.isLastEventLaterThanLastSeen()){
//            createNotificationChannel()
//            //Set the intent to create this activity when notification is pushed.
//            val intent = Intent(mContext, MainActivity::class.java).apply {
//                flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
//            }
//            val pendingIntent: PendingIntent = PendingIntent.getActivity(mContext, 0,intent,0)
//            val listOfEvents = repository.listOfEvents
//
//
//            val builder = NotificationCompat.Builder(mContext, CHANNEL_ID)
//                .setSmallIcon(R.drawable.ic_new_event)
//                .setContentTitle("Nuevos Eventos")
//                .setContentText("Tienes nuevos eventos registrados")
//                .setStyle(
//                    NotificationCompat.BigTextStyle().bigText("El ultimo evento registrado ocurrió con una magnitud de a una profundidad de "))
//                .setContentIntent(pendingIntent)
//                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
//
//            with (NotificationManagerCompat.from(mContext)){
//                notify(notificationId, builder.build())
//            }
//        }
        return Result.success()
    }

//    private fun createNotificationChannel(){
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            val name = "EarthQuake Notification"
//            val descriptionText = "Event Database Notification"
//            val importance = NotificationManager.IMPORTANCE_DEFAULT
//            val channel = NotificationChannel(CHANNEL_ID,name,importance).apply {
//                description= descriptionText
//            }
//            val notificationManager: NotificationManager = applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
//            notificationManager.createNotificationChannel(channel)
//        }
//    }
}