package com.example.data.repository

import com.example.data.model.ResponseResult
import com.example.domain.model.GetAnswerRequest
import com.example.domain.utils.RemoteErrorEmitter

interface RemoteDataSource {
    suspend fun getAnswer(
        remoteErrorEmitter: RemoteErrorEmitter,
        key:String, getAnswerRequest: GetAnswerRequest
    ): ResponseResult?
}