package com.example.kotlinrevision.data.pojo

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "UserId")
data class UserId (
    @PrimaryKey(autoGenerate = true)
    val i: Int,

    @ColumnInfo(name = "user_id")
    val userId: Int
)