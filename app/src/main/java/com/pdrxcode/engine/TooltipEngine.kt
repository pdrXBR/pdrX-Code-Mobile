package com.pdrxcode.engine

class TooltipEngine(
    private val engine: LanguageEngine
) {

    fun getTooltip(word: String): Tooltip? {

        val keyword = engine.config.keywords[word]
        if (keyword != null) {
            return Tooltip(
                name = word,
                description = keyword.description,
                syntax = keyword.syntax,
                example = keyword.example
            )
        }

        val function = engine.config.functions[word]
        if (function != null) {
            return Tooltip(
                name = word,
                description = function.description,
                syntax = function.syntax,
                example = function.example
            )
        }

        return null
    }
}