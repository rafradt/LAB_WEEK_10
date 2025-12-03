package com.example.lab_week_10.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update
import androidx.room.Delete
import androidx.room.Query

@Dao
interface TotalDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(total: Total)

    @Update
    suspend fun update(total: Total)

    @Delete
    suspend fun delete(total: Total)

    @Query("SELECT * FROM total WHERE id = :id")
    suspend fun getTotal(id: Long): List<Total>
}
