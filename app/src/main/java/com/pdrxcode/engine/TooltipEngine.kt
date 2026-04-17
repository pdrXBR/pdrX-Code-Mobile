package com.pdrxcode.engine

class TooltipEngine(
    private val engine: LanguageEngine
) {

    fun getTooltip(word: String): LanguageItem? {
        return engine.config.keywords[word]
            ?: engine.config.functions[word]
            ?: engine.getCharacter(word)
    }
}