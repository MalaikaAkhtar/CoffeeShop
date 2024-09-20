package com.example.coffeeapp.retrofit

import androidx.lifecycle.LiveData
import com.example.coffeeapp.dataclass.Coffee
import retrofit2.Response
import retrofit2.http.GET

interface CoffeeApiService {

    @GET("hot")
    suspend fun getHotCoffee(): Response<List<Coffee>>

    @GET("iced")
    suspend fun getColdCoffee(): Response<List<Coffee>>
}