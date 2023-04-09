package com.example.domain.usecase

import com.example.domain.model.Chatting
import com.example.domain.repository.Repository
import com.example.domain.utils.RemoteErrorEmitter
import javax.inject.Inject

class LocalGetChattingUseCase @Inject constructor(
    private val repository: Repository
) {
    fun excute(remoteErrorEmitter: RemoteErrorEmitter):List<Chatting>{
        return repository.getChatting(remoteErrorEmitter)
    }
}