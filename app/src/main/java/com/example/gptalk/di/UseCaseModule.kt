package com.example.gptalk.di

import com.example.domain.repository.Repository
import com.example.domain.usecase.RequestGetAnswerUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {
    @Provides
    @Singleton
    fun provideRequest(
        repository: Repository
    ) = RequestGetAnswerUseCase(repository)

}