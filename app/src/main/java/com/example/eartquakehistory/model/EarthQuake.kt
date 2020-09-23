package com.example.eartquakehistory.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.eartquakehistory.model.database.DateTimeConverters
import java.time.LocalDateTime
import java.time.OffsetDateTime

@Entity(tableName = "eventdatabase")
class EarthQuake(@PrimaryKey var Fecha : String,
                 val Latitud : String,
                 val Longitud : String,
                 val Profundidad : String,
                 val Magnitud : String,
                 val Agencia : String,
                 val RefGeografica : String,
                 val FechaUpdate : String,
                 @TypeConverters(DateTimeConverters::class)
                 var realDate : LocalDateTime
)