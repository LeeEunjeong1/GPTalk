package com.example.domain.usecase

import com.example.domain.model.Chatting
import com.example.domain.repository.Repository
import com.example.domain.utils.RemoteErrorEmitter
import javax.inject.Inject

class LocalResetChattingUseCase @Inject constructor(
    private val repository: Repository
) {
    fun excute(remoteErrorEmitter: RemoteErrorEmitter):Boolean{
        return repository.resetChatting(remoteErrorEmitter)
    }
}