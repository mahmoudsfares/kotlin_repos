package com.example.kotlinrepos.data_sources.database

import androidx.room.*
import com.example.kotlinrepos.data.pojo.TaskId

@Dao
interface TaskIdDao {

    @Query("SELECT * FROM TaskId LIMIT 1")
    fun getSavedId(): TaskId?

    @Insert
    suspend fun insert(userId: TaskId)

    @Query("DELETE FROM TaskId")
    suspend fun clear()
}
