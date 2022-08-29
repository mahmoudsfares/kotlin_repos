package com.example.kotlinrevision.data

import androidx.room.*
import com.example.kotlinrevision.data.pojo.UserId

@Dao
interface UserIdDao {

    @Query("SELECT * FROM UserId LIMIT 1")
    fun getSavedId(): UserId

    @Insert
    suspend fun insert(userId: UserId)

    @Query("DELETE FROM UserId")
    suspend fun clear()
}