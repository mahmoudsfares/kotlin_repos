package com.example.kotlinrevision.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.kotlinrevision.data.pojo.TaskId


@Database(
    entities = [TaskId::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getTaskIdDao(): TaskIdDao
}
