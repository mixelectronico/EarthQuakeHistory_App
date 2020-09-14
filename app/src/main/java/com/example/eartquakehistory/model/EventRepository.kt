package com.example.eartquakehistory.model

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import com.example.eartquakehistory.model.API.EventRetrofitClient
import com.example.eartquakehistory.model.database.EventDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDate
import java.util.*
import kotlin.collections.ArrayList

class EventRepository(context: Context) {

    private val db : EventDatabase = EventDatabase.getEventReport(context)
    private val listOfEvents : LiveData<List<EarthQuake>> = db.daoEvent().getAllStoredEvents()

    fun bringListOfEventsToViewModel(): LiveData<List<EarthQuake>>{
        return listOfEvents
    }

    fun getEventList(){
            EventRetrofitClient.retrofitInstance().updateLastEvents().enqueue(object : Callback<List<EarthQuake>> {
                override fun onResponse(call: Call<List<EarthQuake>>, response: Response<List<EarthQuake>>
                ) {
                    CoroutineScope(Dispatchers.IO).launch {
                        Log.d("listofevents", response.body().toString())
                        response.body()?.let { db.daoEvent().saveNewEvents(it)}
                    }
                }

                override fun onFailure(call: Call<List<EarthQuake>>, t: Throwable) {
                    Log.e("ERROR", t.toString())
                }
            })
    }
}