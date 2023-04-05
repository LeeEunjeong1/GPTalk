package com.example.domain.repository

import com.example.domain.model.GetAnswerRequest
import com.example.domain.model.GetAnswerResponse
import com.example.domain.utils.RemoteErrorEmitter

interface Repository {
    suspend fun getAnswer(remoteErrorEmitter: RemoteErrorEmitter, getAnswerRequest: GetAnswerRequest): GetAnswerResponse?
}