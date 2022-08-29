package com.example.kotlinrevision.networking

import com.example.kotlinrevision.networking.dto.ServerResponseDTO
import retrofit2.http.GET

interface RetrofitInterface {
    // method is suspended to return the desired data type directly instead of
    // Call<ServerResponseDTO>. this wasn't available before Retrofit supported coroutines.
    @GET("todos/1")
    suspend fun getTitle(): ServerResponseDTO
}