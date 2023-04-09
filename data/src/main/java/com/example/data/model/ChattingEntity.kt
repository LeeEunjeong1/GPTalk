package com.example.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "chatting")
data class ChattingEntity (
    @PrimaryKey(autoGenerate = true)
    val idx: Int?,
    val mode: String,
    val text: String
)