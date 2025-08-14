package com.ai.chat.ollama

data class OllamaRequest(
    val model: String,
    val prompt: String,
    val stream: Boolean = false
) 