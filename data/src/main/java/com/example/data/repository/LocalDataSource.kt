package com.example.data.repository

import com.example.data.model.ChattingEntity
import com.example.domain.model.Chatting

interface LocalDataSource {
    fun insertChatting(chatting: Chatting)
    fun selectAllChatting(): List<ChattingEntity>
    fun deleteAllChatting()
}