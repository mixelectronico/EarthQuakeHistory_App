package com.example.eartquakehistory.model.database

import androidx.room.TypeConverter
import java.time.Instant
import java.time.LocalDateTime
import java.time.OffsetDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

class DateTimeConverters {
    @TypeConverter
    fun fromTimestamp(value: Long?): LocalDateTime? {
        return value?.let { LocalDateTime.ofInstant(Instant.ofEpochSecond(it),ZoneOffset.UTC) }
    }

    @TypeConverter
    fun dateToTimestamp(date: LocalDateTime?): Long? {
        return date?.atZone(ZoneOffset.UTC)?.toInstant()?.toEpochMilli()
    }
}
//    private val formatter = DateTimeFormatter.ISO_DATE_TIME
////
////    @TypeConverter
////    fun toLocalDateTime(value: String?): LocalDateTime?{
////        return LocalDateTime.parse(value,formatter)
////    }
////
////    @TypeConverter
////    fun fromLocalDateTime(date: LocalDateTime):String?{
////        return date.toString()
////    }