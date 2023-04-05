package com.example.data.repository

import com.example.data.BuildConfig
import com.example.data.mapper.Mapper
import com.example.domain.model.GetAnswerRequest
import com.example.domain.model.GetAnswerResponse
import com.example.domain.repository.Repository
import com.example.domain.utils.RemoteErrorEmitter
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource
): Repository {
    override suspend fun getAnswer(
        remoteErrorEmitter: RemoteErrorEmitter,
        getAnswerRequest: GetAnswerRequest
    ): GetAnswerResponse? {
        val responseResult = remoteDataSource.getAnswer(remoteErrorEmitter, BuildConfig.GPT_KEY, getAnswerRequest)
        return responseResult?.let { Mapper.mapperToAnswer(it) }
    }
}