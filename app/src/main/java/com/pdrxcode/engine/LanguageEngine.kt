package com.pdrxcode.engine

class LanguageEngine(
    val config: LanguageConfig
) {

    private val tokenizer = Tokenizer(config)

    fun analyze(code: String): List<Token> {
        return tokenizer.tokenize(code)
    }

    fun getColor(type: String): String {
        return config.colors[type] ?: "#D4D4D4"
    }

    fun getCharacter(name: String): LanguageItem? {
        return config.characters[name]
    }
}