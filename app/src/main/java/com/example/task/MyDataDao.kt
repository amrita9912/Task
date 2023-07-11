package com.example.task

// MyDataDao.kt
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface MyDataDao {
    @Insert
    suspend fun insertData(data: MyData)

    @Query("SELECT * FROM my_data")
    suspend fun getAllData(): List<MyData>
}
