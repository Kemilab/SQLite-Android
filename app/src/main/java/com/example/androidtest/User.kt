package com.example.androidtest

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalTime

@Entity(tableName = "analytics")

data class Data(
    @ColumnInfo(name = "username") var username: String,
    @ColumnInfo(name = "time") var time: String,
    @ColumnInfo(name = "steps") var steps: Int,
    @PrimaryKey(autoGenerate = true) var id: Int = 0
)
{

    fun compltedTime(): LocalTime? = if (time == null) null
        else LocalTime.parse(time)
}