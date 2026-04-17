package com.pdrxcode.engine

object LanguageDetector {

    fun detect(fileName: String): String {
        return when {
            fileName.endsWith(".py") -> "python.json"
            fileName.endsWith(".js") -> "javascript.json"
            fileName.endsWith(".html") -> "html.json"
            else -> "python.json" // fallback
        }
    }
}