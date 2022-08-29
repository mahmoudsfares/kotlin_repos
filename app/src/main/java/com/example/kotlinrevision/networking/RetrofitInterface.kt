package com.example.kotlinrevision.networking

import com.example.kotlinrevision.networking.dto.ServerResponseDTO
import retrofit2.http.GET

interface RetrofitInterface {
    @GET("todos/1")
    suspend fun getTitle(): ServerResponseDTO
}