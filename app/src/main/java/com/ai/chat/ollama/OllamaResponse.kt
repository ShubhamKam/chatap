package com.ai.chat.ollama

data class OllamaResponse(
    val model: String,
    val created_at: String,
    val response: String,
    val done: Boolean
) 