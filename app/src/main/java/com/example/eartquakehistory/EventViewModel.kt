package com.example.eartquakehistory

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.work.*
import com.example.eartquakehistory.model.EarthQuake
import com.example.eartquakehistory.model.EventRepository
import com.example.eartquakehistory.model.UpdateWorker
import java.util.concurrent.TimeUnit

class EventViewModel(application: Application): AndroidViewModel(application) {
    private val repository = EventRepository(application)
    private val listOfEvents : LiveData<List<EarthQuake>> = repository.bringListOfEventsToViewModel()
    private val workManager = WorkManager.getInstance(application)
    private val uploadWorkRequest: WorkRequest = PeriodicWorkRequest.Builder(UpdateWorker::class.java,250,TimeUnit.SECONDS).build()

    fun bringListOfEvents_ToView(): LiveData<List<EarthQuake>>{
        return listOfEvents
    }

    fun updateEventList(){
        repository.getEventList()
    }

    fun updateDatabasePeriodically(){
        workManager.enqueue(uploadWorkRequest)
    }

    fun saveLastViewedEvents(){
        repository.setLastViewedTimeInSharedPreferences()
    }

    fun startNotificationChannel(){
        repository.createNotificationChannel()
    }
}