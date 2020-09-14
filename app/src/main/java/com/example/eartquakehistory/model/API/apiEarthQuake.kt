package com.example.eartquakehistory.model.API

import com.example.eartquakehistory.model.EarthQuake
import retrofit2.Call
import retrofit2.http.GET

interface apiEarthQuake {

@GET("/general/public/sismos")
fun updateLastEvents(): Call<List<EarthQuake>>

}