package com.pdrxcode.engine

class AutocompleteEngine(
    private val engine: LanguageEngine
) {

    fun getSuggestions(text: String, cursorIndex: Int): List<String> {

        val prefix = extractPrefix(text, cursorIndex)

        if (prefix.isBlank()) return emptyList()

        val keywords = engine.config.keywords.keys
        val functions = engine.config.functions.keys

        return (keywords + functions)
            .filter { it.startsWith(prefix) }
            .sorted()
            .take(10)
    }

    private fun extractPrefix(text: String, index: Int): String {
        if (index <= 0) return ""

        val sub = text.substring(0, index)

        return sub.takeLastWhile {
            it.isLetterOrDigit() || it == '_'
        }
    }
}