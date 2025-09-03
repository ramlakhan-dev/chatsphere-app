package com.rl.chatsphere.di

import com.google.ai.client.generativeai.GenerativeModel
import com.rl.chatsphere.BuildConfig
import com.rl.chatsphere.data.repository.ChatRepositoryImpl
import com.rl.chatsphere.domain.repository.ChatRepository
import com.rl.chatsphere.domain.usecase.chatusecase.ChatUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Provides
    @Singleton
    fun provideGenerativeModel(): GenerativeModel = GenerativeModel(
        modelName = "gemini-2.5-flash-lite",
        apiKey = BuildConfig.apiKey
    )

    @Provides
    @Singleton
    fun provideChatRepository(generativeModel: GenerativeModel): ChatRepository = ChatRepositoryImpl(generativeModel)

    @Provides
    @Singleton
    fun provideChatUseCaseRepository(chatRepository: ChatRepository): ChatUseCase = ChatUseCase(chatRepository)
}