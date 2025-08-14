package com.ai.chat.api

data class ChatResponse(
    val id: String,
    val `object`: String,
    val created: Long,
    val model: String,
    val choices: List<Choice>
)

data class Choice(
    val message: Message,
    val finish_reason: String,
    val index: Int
) 