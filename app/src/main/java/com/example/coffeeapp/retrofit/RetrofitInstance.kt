package com.example.coffeeapp.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    private const val BASE_URL = "https://api.sampleapis.com/coffee/"

    private fun getInstance(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    val coffeeApi : CoffeeApiService = getInstance().create(CoffeeApiService::class.java)
}


//https://api.sampleapis.com/coffee/hot
//https://api.sampleapis.com/coffee/iced