package com.example.kotlinrepos.networking

import com.example.kotlinrepos.networking.dto.ServerResponseDTO
import retrofit2.http.GET
import retrofit2.http.Path

interface RetrofitInterface {
    // method is suspended to return the desired data type directly instead of
    // Call<ServerResponseDTO>. this wasn't available before Retrofit supported coroutines.
    @GET("todos/{id}")
    suspend fun getTitle(@Path(value = "id") taskId: Int): ServerResponseDTO
}