package com.example.lab_week_10.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Total::class], version = 1)
abstract class TotalDatabase : RoomDatabase() {

    abstract fun totalDao(): TotalDao

    companion object {
        @Volatile
        private var INSTANCE: TotalDatabase? = null

        fun getInstance(context: Context): TotalDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TotalDatabase::class.java,
                    "total_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
