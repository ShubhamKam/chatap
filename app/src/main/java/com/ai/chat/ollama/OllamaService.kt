package com.ai.chat.ollama

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface OllamaService {
    @POST("api/generate")
    fun generateResponse(@Body request: OllamaRequest): Call<OllamaResponse>
} 