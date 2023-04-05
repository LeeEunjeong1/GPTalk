package com.example.gptalk.di

import com.example.data.api.ApiInterface
import com.example.data.repository.RemoteDataSource
import com.example.data.repository.RemoteDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {
    @Provides
    @Singleton
    fun provideRemoteDataSource(
        apiInterface: ApiInterface
    ): RemoteDataSource =
        RemoteDataSourceImpl(apiInterface)

}