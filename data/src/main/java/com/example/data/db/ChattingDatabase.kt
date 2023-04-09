package com.example.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.data.model.ChattingEntity

@Database(entities = [ChattingEntity::class], version = 1, exportSchema = false)
abstract class ChattingDatabase : RoomDatabase() {
    abstract fun chattingDao(): ChattingDao
}