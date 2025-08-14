package com.ai.chat.error

class ErrorResolver {
    
    fun suggestFix(errorMessage: String): String {
        return when {
            errorMessage.contains("SyntaxError") -> "Check for missing colons, parentheses, or indentation issues."
            errorMessage.contains("NameError") -> "Variable or function name is not defined. Check spelling and scope."
            errorMessage.contains("TypeError") -> "Check data types. You might be mixing incompatible types."
            errorMessage.contains("IndexError") -> "List index is out of range. Check your list length and index values."
            errorMessage.contains("KeyError") -> "Dictionary key doesn't exist. Check your key names."
            errorMessage.contains("AttributeError") -> "Object doesn't have the attribute you're trying to access."
            errorMessage.contains("ImportError") -> "Module not found. Check if it's installed or spelled correctly."
            errorMessage.contains("FileNotFoundError") -> "File path is incorrect or file doesn't exist."
            else -> "Review your code logic and check for common programming mistakes."
        }
    }
    
    fun getDetailedFix(errorMessage: String, code: String): String {
        val basicFix = suggestFix(errorMessage)
        return "$basicFix\n\nCode snippet:\n$code\n\nTry debugging step by step."
    }
} 