package com.rl.chatsphere.presentation.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rl.chatsphere.domain.model.Message
import com.rl.chatsphere.domain.usecase.chatusecase.ChatUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val chatUseCase: ChatUseCase
): ViewModel() {

    private val _messages = MutableStateFlow<List<Message>>(emptyList())
    val messages: StateFlow<List<Message>> = _messages.asStateFlow()

    fun sendMessage(prompt: String) {
        viewModelScope.launch {
            val userMessage = Message(prompt, true)
            _messages.update { it + userMessage }

            val history = _messages.value
            val response = chatUseCase(prompt, history)
            val modelMessage = Message(response, false)

            _messages.update { it + modelMessage }
        }
    }
}