package com.example.androidtest.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addActivity(user: User)

    @Query("SELECT * FROM analysis ORDER BY id ASC")
    fun readAllData(): LiveData<List<User>>

    @Query("DELETE FROM analysis")
    suspend fun deleteAllData()

}