package com.example.data.repository

import com.example.data.db.ChattingDao
import com.example.data.model.ChattingEntity
import com.example.data.utils.BaseRepository
import com.example.domain.model.Chatting
import javax.inject.Inject

class LocalDataSourceImpl @Inject constructor(private val chattingDao: ChattingDao) : BaseRepository(),
    LocalDataSource {
    override fun insertChatting(chatting: Chatting) =
        chattingDao.insertChatting(ChattingEntity(null,chatting.mode,chatting.text))

    override fun selectAllChatting(): List<ChattingEntity> = chattingDao.selectAllChatting()

}