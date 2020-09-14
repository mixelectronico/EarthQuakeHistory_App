package com.example.eartquakehistory.model.API

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class EventRetrofitClient {
    companion object{
        private const val BASE_URL = "https://api.gael.cl"
        fun retrofitInstance():apiEarthQuake {
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return retrofit.create(apiEarthQuake::class.java)
        }
    }
}