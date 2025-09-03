package com.rl.chatsphere.domain.usecase.chatusecase

import com.rl.chatsphere.domain.model.Message
import com.rl.chatsphere.domain.repository.ChatRepository
import javax.inject.Inject

class ChatUseCase @Inject constructor(
    private val chatRepository: ChatRepository
) {
    suspend operator fun invoke(prompt: String, history: List<Message>) = chatRepository.sendMessage(prompt, history)
}