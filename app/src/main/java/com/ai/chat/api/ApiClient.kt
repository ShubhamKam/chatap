package com.ai.chat.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    private const val OPENAI_BASE_URL = "https://api.openai.com/v1/"
    private const val CLAUDE_BASE_URL = "https://api.anthropic.com/v1/"
    private const val GROK_BASE_URL = "https://api.x.ai/v1/"

    private val okHttpClient = OkHttpClient.Builder().build()

    fun getOpenAiService(): ApiService {
        return Retrofit.Builder()
            .baseUrl(OPENAI_BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }

    fun getClaudeService(): ApiService {
        return Retrofit.Builder()
            .baseUrl(CLAUDE_BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }

    fun getGrokService(): ApiService {
        return Retrofit.Builder()
            .baseUrl(GROK_BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
} 