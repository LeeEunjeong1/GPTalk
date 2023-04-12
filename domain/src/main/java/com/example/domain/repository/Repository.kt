package com.example.domain.repository

import com.example.domain.model.Chatting
import com.example.domain.model.GetAnswerRequest
import com.example.domain.model.GetAnswerResponse
import com.example.domain.utils.RemoteErrorEmitter

interface Repository {
    fun setChatting(remoteErrorEmitter: RemoteErrorEmitter,chatting:Chatting):Boolean
    fun getChatting(remoteErrorEmitter: RemoteErrorEmitter):List<Chatting>
    fun resetChatting(remoteErrorEmitter: RemoteErrorEmitter):Boolean
    suspend fun getAnswer(remoteErrorEmitter: RemoteErrorEmitter, getAnswerRequest: GetAnswerRequest): GetAnswerResponse?
}