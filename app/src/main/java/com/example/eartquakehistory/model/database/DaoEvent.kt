package com.example.eartquakehistory.model.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.eartquakehistory.model.EarthQuake

@Dao
interface DaoEvent {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveNewEvents(newEventList : List<EarthQuake>)

    @Query("SELECT * FROM eventdatabase ORDER BY realDate DESC")
    fun getAllStoredEvents() : LiveData<List<EarthQuake>>

    @Query("SELECT * FROM eventdatabase WHERE Fecha = :fechaQuery")
    fun isEventRegistered(fechaQuery : String) : EarthQuake

    @Query("SELECT MAX(realDate) FROM eventdatabase")
    fun getLastDateEvent(): Long
}