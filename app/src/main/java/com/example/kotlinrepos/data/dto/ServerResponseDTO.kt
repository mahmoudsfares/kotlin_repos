package com.example.kotlinrepos.data.dto

import com.google.gson.annotations.SerializedName

data class ServerResponseDTO(
    @SerializedName("title")
    val title: String
)
