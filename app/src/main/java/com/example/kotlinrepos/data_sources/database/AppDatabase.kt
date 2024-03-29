package com.example.kotlinrepos.data_sources.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.kotlinrepos.data.pojo.Task


@Database(
    entities = [Task::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getTaskIdDao(): TaskDao
}
