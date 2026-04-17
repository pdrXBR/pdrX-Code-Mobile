package com.pdrxcode.engine

class AutocompleteEngine(
    private val engine: LanguageEngine
) {

    fun getSuggestions(code: String, cursor: Int): List<String> {
        val prefix = getCurrentWord(code, cursor)

        val keywords = engine.config.keywords.keys
        val functions = engine.config.functions.keys
        val characters = engine.config.characters.keys

        return (keywords + functions + characters)
            .filter { it.startsWith(prefix) && it != prefix }
            .sorted()
            .take(8)
    }

    fun applySuggestion(
        text: String,
        cursor: Int,
        suggestion: String
    ): Pair<String, Int> {

        val prefix = getCurrentWord(text, cursor)

        val start = cursor - prefix.length

        val newText = text.replaceRange(start, cursor, suggestion)

        return Pair(newText, start + suggestion.length)
    }

    private fun getCurrentWord(text: String, cursor: Int): String {
        if (text.isEmpty()) return ""

        val safeIndex = cursor.coerceIn(0, text.length)

        return text.take(safeIndex)
            .takeLastWhile { it.isLetterOrDigit() || it == '_' }
    }
}