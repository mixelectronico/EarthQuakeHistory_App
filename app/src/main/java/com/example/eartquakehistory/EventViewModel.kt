package com.example.eartquakehistory

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.eartquakehistory.model.EarthQuake
import com.example.eartquakehistory.model.EventRepository

class EventViewModel(application: Application): AndroidViewModel(application) {
    private val repository = EventRepository(application)
    private val listOfEvents : LiveData<List<EarthQuake>> = repository.bringListOfEventsToViewModel()

    fun bringListOfEvents_ToView(): LiveData<List<EarthQuake>>{
        return listOfEvents
    }

    fun updateEventList(){
        repository.getEventList()
    }

}