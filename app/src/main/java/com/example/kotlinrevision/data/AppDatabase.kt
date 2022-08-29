package com.example.kotlinrevision.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.kotlinrevision.data.pojo.UserId


@Database(
    entities = [UserId::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getUserIdDao(): UserIdDao
}
