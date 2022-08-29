package com.example.kotlinrevision.networking.dto

import com.google.gson.annotations.SerializedName

data class ServerResponseDTO(
    @SerializedName("title")
    val title: String
)