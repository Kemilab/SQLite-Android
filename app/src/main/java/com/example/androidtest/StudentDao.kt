package com.example.androidtest

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


@Dao
interface StudentDao {
   @Query("SELECT * FROM analytics")
   fun getAll(): List<Data>

   @Insert(onConflict = OnConflictStrategy.IGNORE)
   suspend fun insert(data: Data)

   @Delete
   suspend fun delete(data: Data)

   @Query("DELETE FROM analytics")
   suspend fun deleteAll()

}