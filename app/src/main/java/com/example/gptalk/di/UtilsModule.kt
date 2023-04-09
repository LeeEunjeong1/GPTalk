package com.example.gptalk.di

import android.content.Context
import androidx.room.Room
import com.example.data.db.ChattingDao
import com.example.data.db.ChattingDatabase
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

    @Provides
    @Singleton
    fun provideRoom(@ApplicationContext context: Context):ChattingDatabase{
        return Room.databaseBuilder(
            context,
            ChattingDatabase::class.java,
            "Chatting.db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideChattingDao(chattingDatabase: ChattingDatabase): ChattingDao{
        return chattingDatabase.chattingDao()
    }

}