package com.rl.chatsphere.domain.repository

import com.rl.chatsphere.domain.model.Message

interface ChatRepository {

    suspend fun sendMessage(prompt: String, history: List<Message>): String
}