package com.ai.chat.code

import android.content.Context
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.File
import java.io.FileWriter

class CodeExecutor(private val context: Context) {
    
    fun executePythonCode(code: String): CodeExecutionResult {
        return try {
            // Create a temporary Python file
            val tempFile = File(context.cacheDir, "temp_code.py")
            FileWriter(tempFile).use { it.write(code) }
            
            // Execute the Python code
            val process = Runtime.getRuntime().exec("python3 ${tempFile.absolutePath}")
            
            // Read the output
            val output = BufferedReader(InputStreamReader(process.inputStream)).readText()
            val error = BufferedReader(InputStreamReader(process.errorStream)).readText()
            
            // Wait for the process to complete
            val exitCode = process.waitFor()
            
            // Clean up
            tempFile.delete()
            
            if (exitCode == 0) {
                CodeExecutionResult.Success(output)
            } else {
                CodeExecutionResult.Error(error)
            }
        } catch (e: Exception) {
            CodeExecutionResult.Error("Execution error: ${e.message}")
        }
    }
    
    sealed class CodeExecutionResult {
        data class Success(val output: String) : CodeExecutionResult()
        data class Error(val error: String) : CodeExecutionResult()
    }
} 