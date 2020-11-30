package com.example.eartquakehistory.model

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.LiveData
import com.example.eartquakehistory.R
import com.example.eartquakehistory.model.API.EventRetrofitClient
import com.example.eartquakehistory.model.database.EventDatabase
import com.example.eartquakehistory.view.MainActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDateTime
import java.time.ZoneOffset

class EventRepository(context: Context) {
    private val db : EventDatabase = EventDatabase.getEventReport(context)
    val listOfEvents : LiveData<List<EarthQuake>> = db.daoEvent().getAllStoredEvents()
    var SharedPreferencesFileName = "com.example.earthQuakehistory"
    var mSharedPreferences : SharedPreferences
    private val CHANNEL_ID = "channel_id_example_01"
    private val notificationId = 101
    private val thisContext = context.applicationContext

    init{
        mSharedPreferences = context.getSharedPreferences(SharedPreferencesFileName, Context.MODE_PRIVATE)
    }

    fun setLastViewedTimeInSharedPreferences(){
        CoroutineScope(Dispatchers.IO).launch {
            val dateAsLong = db.daoEvent().getLastDateEvent()
            mSharedPreferences.edit().putLong("lastSeenDate",dateAsLong).apply()
        }
    }

    fun setLastNotificationTimeInSharedPreferences(){
        CoroutineScope(Dispatchers.IO).launch {
            val dateAsLong = db.daoEvent().getLastDateEvent()
            mSharedPreferences.edit().putLong("lastNotificationDate",dateAsLong).apply()
        }
    }

    fun bringListOfEventsToViewModel(): LiveData<List<EarthQuake>>{
        return listOfEvents
    }

    fun getEventList(){
        Log.d("SUBTAREA", "LA TAREA SE ESTA EJECUTANDO")
        EventRetrofitClient.retrofitInstance().updateLastEvents().enqueue(object : Callback<List<EarthQuake>> {
            override fun onResponse(call: Call<List<EarthQuake>>, response: Response<List<EarthQuake>>
            ) {
                CoroutineScope(Dispatchers.IO).launch {
                    response.body()?.let {
                        it.forEach {
                            it.Fecha = it.Fecha.replace(' ', 'T', false)
                            it.Fecha = it.Fecha.replace('/', '-', false)
                            it.realDate = LocalDateTime.parse(it.Fecha)
                        }
                        db.daoEvent().saveNewEvents(it)
                        compareDates(it[0])
                    }
                }
            }

            override fun onFailure(call: Call<List<EarthQuake>>, t: Throwable) {
                Log.e("ERROR", t.toString())
            }
        })
    }

    fun compareDates(lastEvent: EarthQuake){
        val lastEventDate = lastEvent.realDate
        val lastEventDateAsLong = lastEventDate.atZone(ZoneOffset.UTC).toInstant().toEpochMilli()
        val lastNotificatedEventDate = mSharedPreferences.getLong("lastNotificationDate", lastEventDateAsLong)
        val lastSeenEventDate = mSharedPreferences.getLong("lastSeenDate", lastEventDateAsLong)
        val unseenEvents = db.daoEvent().getUnseenEvents(lastSeenEventDate)
        if (lastEventDateAsLong!! > lastNotificatedEventDate){
            Log.d("CHEQUEOFECHA", "SE ACTIVA LA NOTIFICACION")
            activateNotification(unseenEvents)
        }else{
            Log.d("CHEQUEOFECHA", "NO SE ACTIVA LA NOTIFICACION")
        }
    }

    fun createNotificationChannel(){
        val context = thisContext.applicationContext
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "EarthQuake Notification"
            val descriptionText = "Event Database Notification"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID,name,importance).apply {
                description= descriptionText
            }
            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    fun activateNotification(unseenEvents: List<EarthQuake>){
        setLastNotificationTimeInSharedPreferences()
        val context = thisContext.applicationContext
        //Set the intent to create this activity when notification is pushed.
        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        }
        val pendingIntent: PendingIntent = PendingIntent.getActivity(context, 0,intent,0)

        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_terremoto_registro_black)
            .setContentTitle("Nuevos Eventos")
            .setContentText("Tienes ${unseenEvents.size} nuevos eventos registrados")
            .setStyle(
                NotificationCompat.BigTextStyle().bigText("El ultimo evento registrado ocurrió a ${unseenEvents[0].RefGeografica}" +
                        " con una magnitud de ${unseenEvents[0].Magnitud} a una profundidad de ${unseenEvents[0].Profundidad}"))
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        with (NotificationManagerCompat.from(context)){
            notify(notificationId, builder.build())
        }
    }
}