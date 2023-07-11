package com.example.task

// AppDatabase.kt
import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [MyData::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun myDataDao(): MyDataDao
}

