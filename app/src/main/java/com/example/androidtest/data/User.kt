package com.example.androidtest.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "analysis")
data class User(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val time:  String,
    val steps: Int
)

