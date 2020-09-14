package com.example.eartquakehistory.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "eventdatabase")
class EarthQuake(@PrimaryKey val Fecha : String,
    val Latitud : String,
    val Longitud : String,
    val Profundidad : String,
    val Magnitud : String,
    val Agencia : String,
    val RefGeografica : String,
    val FechaUpdate : String,
)