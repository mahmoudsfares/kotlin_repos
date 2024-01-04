package com.example.kotlinrepos.data_sources.database

import androidx.room.*
import com.example.kotlinrepos.data.pojo.Task

@Dao
interface TaskDao {

    @Query("SELECT * FROM TaskEntity LIMIT 1")
    fun getSavedId(): Task?

    @Insert
    suspend fun insert(task: Task)

    @Query("DELETE FROM TaskEntity")
    suspend fun clear()
}
