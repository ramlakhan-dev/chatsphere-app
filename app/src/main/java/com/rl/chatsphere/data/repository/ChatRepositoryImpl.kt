package com.rl.chatsphere.data.repository

import com.google.ai.client.generativeai.GenerativeModel
import com.rl.chatsphere.data.mapper.toContent
import com.rl.chatsphere.domain.model.Message
import com.rl.chatsphere.domain.repository.ChatRepository
import javax.inject.Inject

class ChatRepositoryImpl @Inject constructor(
    private val generativeModel: GenerativeModel
): ChatRepository {
    override suspend fun sendMessage(prompt: String, history: List<Message>): String {
        val chat = generativeModel.startChat(
            history = history.map { it.toContent() }
        )
        val response = chat.sendMessage(prompt)
        return response.text.toString()
    }
}