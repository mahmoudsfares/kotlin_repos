package com.example.kotlinrevision.data.pojo

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "TaskId")
data class TaskId(
    @ColumnInfo(name = "task_id")
    val taskId: Int
) {
    @PrimaryKey(autoGenerate = true)
    var i: Int = 0
}
