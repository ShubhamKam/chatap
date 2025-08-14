package com.ai.chat

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.widget.EditText
import android.widget.Button
import com.ai.chat.api.ApiClient
import com.ai.chat.api.ChatRequest
import com.ai.chat.api.Message
import com.ai.chat.api.ChatResponse
import com.ai.chat.code.CodeExecutor
import com.ai.chat.storage.CodeFileManager
import com.ai.chat.error.ErrorResolver
import com.ai.chat.ui.JupyterRenderer

class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var messageEditText: EditText
    private lateinit var sendButton: Button
    private val messages = mutableListOf<Message>()
    private lateinit var adapter: ChatAdapter
    
    private lateinit var codeExecutor: CodeExecutor
    private lateinit var codeFileManager: CodeFileManager
    private lateinit var errorResolver: ErrorResolver
    private lateinit var jupyterRenderer: JupyterRenderer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)
        messageEditText = findViewById(R.id.messageEditText)
        sendButton = findViewById(R.id.sendButton)

        // Initialize services
        codeExecutor = CodeExecutor(this)
        codeFileManager = CodeFileManager(this)
        errorResolver = ErrorResolver()
        jupyterRenderer = JupyterRenderer(this)

        adapter = ChatAdapter(messages)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        sendButton.setOnClickListener {
            val userMessage = messageEditText.text.toString().trim()
            if (userMessage.isNotEmpty()) {
                messages.add(Message("user", userMessage))
                adapter.notifyItemInserted(messages.size - 1)
                messageEditText.text.clear()
                
                // Check if message contains code execution request
                if (userMessage.contains("```python") && userMessage.contains("```")) {
                    executeCodeFromMessage(userMessage)
                } else {
                    // Call API to get response from LLM
                    callLLMApi(userMessage)
                }
            }
        }
    }

    private fun executeCodeFromMessage(message: String) {
        val codeStart = message.indexOf("```python") + 9
        val codeEnd = message.indexOf("```", codeStart)
        if (codeStart > 8 && codeEnd > codeStart) {
            val code = message.substring(codeStart, codeEnd).trim()
            
            // Execute the code
            val result = codeExecutor.executePythonCode(code)
            
            when (result) {
                is CodeExecutor.CodeExecutionResult.Success -> {
                    messages.add(Message("assistant", "Code executed successfully:\n$code\n\nOutput:\n${result.output}"))
                }
                is CodeExecutor.CodeExecutionResult.Error -> {
                    val errorFix = errorResolver.getDetailedFix(result.error, code)
                    messages.add(Message("assistant", "Code execution failed:\n$code\n\nError:\n${result.error}\n\nSuggestion:\n$errorFix"))
                }
            }
            
            adapter.notifyItemInserted(messages.size - 1)
            recyclerView.scrollToPosition(messages.size - 1)
        }
    }

    private fun callLLMApi(userMessage: String) {
        // For now, using OpenAI as default
        val apiService = ApiClient.getOpenAiService()
        val chatRequest = ChatRequest(
            model = "gpt-3.5-turbo",
            messages = listOf(Message("user", userMessage))
        )
        
        // Note: You'll need to add your API key here
        val apiKey = "Bearer YOUR_API_KEY_HERE"
        
        apiService.getChatCompletion(apiKey, chatRequest).enqueue(object : retrofit2.Callback<ChatResponse> {
            override fun onResponse(call: retrofit2.Call<ChatResponse>, response: retrofit2.Response<ChatResponse>) {
                if (response.isSuccessful) {
                    val aiResponse = response.body()?.choices?.firstOrNull()?.message?.content ?: "No response"
                    messages.add(Message("assistant", aiResponse))
                    runOnUiThread {
                        adapter.notifyItemInserted(messages.size - 1)
                        recyclerView.scrollToPosition(messages.size - 1)
                    }
                } else {
                    // Handle error
                    val errorMessage = "Error: ${response.code()}"
                    messages.add(Message("system", errorMessage))
                    runOnUiThread {
                        adapter.notifyItemInserted(messages.size - 1)
                    }
                }
            }

            override fun onFailure(call: retrofit2.Call<ChatResponse>, t: Throwable) {
                val errorMessage = "Network error: ${t.message}"
                messages.add(Message("system", errorMessage))
                runOnUiThread {
                    adapter.notifyItemInserted(messages.size - 1)
                }
            }
        })
    }
} 