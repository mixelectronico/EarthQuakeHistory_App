package com.example.eartquakehistory.model

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.LiveData
import com.example.eartquakehistory.model.API.EventRetrofitClient
import com.example.eartquakehistory.model.database.EventDatabase
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset

class EventRepository(context: Context) {
    private val db : EventDatabase = EventDatabase.getEventReport(context)
    val listOfEvents : LiveData<List<EarthQuake>> = db.daoEvent().getAllStoredEvents()
    var SharedPreferencesFileName = "com.example.earthQuakehistory"
    var mSharedPreferences : SharedPreferences

    init{
        mSharedPreferences = context.getSharedPreferences(SharedPreferencesFileName, Context.MODE_PRIVATE)
    }

    fun setLastViewedTimeInSharedPreferences(){
        CoroutineScope(Dispatchers.IO).launch {
            val dateAsLong = db.daoEvent().getLastDateEvent()
            mSharedPreferences.edit().putLong("lastSeenDate",dateAsLong).apply()
        }
    }

    fun isLastEventLaterThanLastSeen():Boolean{
        val lastUpdatedDateTimeEvent = listOfEvents.value?.get(0)?.realDate
        val lastSeenEventDate = mSharedPreferences.getLong("lastSeenDate",
            lastUpdatedDateTimeEvent?.atZone(ZoneOffset.UTC)?.toInstant()?.toEpochMilli()!!
        )
        if (lastUpdatedDateTimeEvent > LocalDateTime.ofInstant(Instant.ofEpochSecond(lastSeenEventDate), ZoneOffset.UTC)){
            return true
        }
        return false
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
                    }
                }
            }

            override fun onFailure(call: Call<List<EarthQuake>>, t: Throwable) {
                Log.e("ERROR", t.toString())
            }
        })
    }
}