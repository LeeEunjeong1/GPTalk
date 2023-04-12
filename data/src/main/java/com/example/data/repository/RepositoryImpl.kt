package com.example.data.repository

import com.example.data.BuildConfig
import com.example.data.mapper.Mapper
import com.example.domain.model.Chatting
import com.example.domain.model.GetAnswerRequest
import com.example.domain.model.GetAnswerResponse
import com.example.domain.repository.Repository
import com.example.domain.utils.RemoteErrorEmitter
import com.example.domain.utils.Util
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
): Repository {
    // local
    override fun setChatting(remoteErrorEmitter: RemoteErrorEmitter, chatting:Chatting): Boolean {
        localDataSource.insertChatting(chatting)
        Util.logMessage("setChatting :: $chatting")
        return true
    }
    override fun getChatting(remoteErrorEmitter: RemoteErrorEmitter): List<Chatting> {
        val selectChatting = localDataSource.selectAllChatting()
        return selectChatting.let{Mapper.mapperToChatting(it)}
    }
    override fun resetChatting(remoteErrorEmitter: RemoteErrorEmitter):Boolean{
        localDataSource.deleteAllChatting()
        return true
    }
    // remote
    override suspend fun getAnswer(
        remoteErrorEmitter: RemoteErrorEmitter,
        getAnswerRequest: GetAnswerRequest
    ): GetAnswerResponse? {
        val responseResult = remoteDataSource.getAnswer(remoteErrorEmitter, BuildConfig.GPT_KEY, getAnswerRequest)
        return responseResult?.let {
            Mapper.mapperToAnswer(it)
        }
    }
}