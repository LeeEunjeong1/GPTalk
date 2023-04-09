package com.example.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.data.model.ChattingEntity

@Dao
interface ChattingDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertChatting(chatting: ChattingEntity)

    @Query("SELECT * FROM chatting")
    fun selectAllChatting(): List<ChattingEntity>

}