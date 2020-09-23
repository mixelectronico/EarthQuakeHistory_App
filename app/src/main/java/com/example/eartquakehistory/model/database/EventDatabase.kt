package com.example.eartquakehistory.model.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.eartquakehistory.model.EarthQuake

@Database(entities = [EarthQuake::class], version = 1)
@TypeConverters(DateTimeConverters::class)
abstract class EventDatabase : RoomDatabase() {
    abstract fun daoEvent() : DaoEvent
    companion object{
        @Volatile
        private var INSTANCE : EventDatabase? = null

        fun getEventReport(context: Context): EventDatabase{
            val tempInstance = INSTANCE
            if (tempInstance != null){
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    EventDatabase::class.java,
                    "informaDeSismos").build()
                INSTANCE = instance
                return instance
            }
        }
    }
}

//fun getPhotoDB(context: Context):PhotoDataRoomBase{
//    if(INSTANCE == null)
//        synchronized(this){
//            INSTANCE = Room.databaseBuilder(context.applicationContext, PhotoDataRoomBase::class.java, DBNAME).build()
//        }
//    return INSTANCE!!
//}