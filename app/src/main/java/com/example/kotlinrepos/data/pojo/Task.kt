package com.example.kotlinrepos.data.pojo

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "TaskEntity")
data class Task(
    @ColumnInfo(name = "id")
    val id: Int
) {
    @PrimaryKey(autoGenerate = true)
    private var i: Int = 0
}
