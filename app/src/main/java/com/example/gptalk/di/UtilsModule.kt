package com.example.gptalk.di

import android.content.Context
import com.example.data.utils.PrefUtil
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UtilsModule {
    @Provides
    @Singleton
    fun providePrefUtil(
        @ApplicationContext context: Context
    ) = PrefUtil(context)


}