package com.example.task

// MyData.kt
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "my_data")
data class MyData(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val channel: String,
    val name: String,
    val time: Long
)
