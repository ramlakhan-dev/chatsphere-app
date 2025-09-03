package com.rl.chatsphere.data.mapper

import com.google.ai.client.generativeai.type.Content
import com.google.ai.client.generativeai.type.TextPart
import com.rl.chatsphere.domain.model.Message

fun Message.toContent(): Content {
    val role = if (isUser) "user" else "model"
    return Content(
        role = role,
        parts = listOf(TextPart(text))
    )
}