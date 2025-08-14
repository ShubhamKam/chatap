package com.ai.chat.storage

import android.content.Context
import java.io.File
import java.io.FileWriter
import java.io.BufferedReader
import java.io.InputStreamReader

class CodeFileManager(private val context: Context) {
    
    private val codeDirectory = File(context.filesDir, "python_code")
    
    init {
        if (!codeDirectory.exists()) {
            codeDirectory.mkdirs()
        }
    }
    
    fun saveCodeFile(filename: String, code: String): Boolean {
        return try {
            val file = File(codeDirectory, "$filename.py")
            FileWriter(file).use { it.write(code) }
            true
        } catch (e: Exception) {
            false
        }
    }
    
    fun loadCodeFile(filename: String): String? {
        return try {
            val file = File(codeDirectory, "$filename.py")
            if (file.exists()) {
                BufferedReader(InputStreamReader(file.inputStream())).readText()
            } else {
                null
            }
        } catch (e: Exception) {
            null
        }
    }
    
    fun listCodeFiles(): List<String> {
        return codeDirectory.listFiles()
            ?.filter { it.extension == "py" }
            ?.map { it.nameWithoutExtension }
            ?: emptyList()
    }
    
    fun deleteCodeFile(filename: String): Boolean {
        return try {
            val file = File(codeDirectory, "$filename.py")
            file.delete()
        } catch (e: Exception) {
            false
        }
    }
} 