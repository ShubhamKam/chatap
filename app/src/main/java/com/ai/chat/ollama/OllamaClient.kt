package com.ai.chat.ollama

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object OllamaClient {
    private const val OLLAMA_BASE_URL = "http://10.0.2.2:11434/" // For Android emulator

    private val okHttpClient = OkHttpClient.Builder().build()

    fun getOllamaService(): OllamaService {
        return Retrofit.Builder()
            .baseUrl(OLLAMA_BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(OllamaService::class.java)
    }
} 