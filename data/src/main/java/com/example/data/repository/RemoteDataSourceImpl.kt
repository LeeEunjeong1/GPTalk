package com.example.data.repository

import com.example.data.api.ApiInterface
import com.example.data.model.GetAnswerRequest
import com.example.data.model.ResponseResult
import com.example.data.utils.BaseRepository
import com.example.domain.utils.RemoteErrorEmitter
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(
    private val api: ApiInterface
) : BaseRepository(), RemoteDataSource{
    override suspend fun getAnswer(
        remoteErrorEmitter: RemoteErrorEmitter,
        key: String,
        getAnswerRequest: com.example.domain.model.GetAnswerRequest
    ): ResponseResult? {
        return safeApiCallData(remoteErrorEmitter) {
            api.getAnswer(
                key = key,
                GetAnswerRequest(
                    model=  "text-davinci-003",
                    prompt= getAnswerRequest.prompt,
                    temperature= getAnswerRequest.temperature,
                    max_tokens=3900,
                    top_p=1,
                    frequency_penalty=getAnswerRequest.frequency_penalty,
                    presence_penalty=0
                )
            )
        }
    }
}