package com.example.domain.usecase

import com.example.domain.model.GetAnswerRequest
import com.example.domain.model.GetAnswerResponse
import com.example.domain.repository.Repository
import com.example.domain.utils.RemoteErrorEmitter
import javax.inject.Inject

class RequestGetAnswerUseCase @Inject constructor(
    private val repository: Repository
) {
    suspend fun excute(
        remoteErrorEmitter: RemoteErrorEmitter,
        getAnswerRequest:GetAnswerRequest
    ):GetAnswerResponse?{
        return repository.getAnswer(remoteErrorEmitter,getAnswerRequest)
    }
}
