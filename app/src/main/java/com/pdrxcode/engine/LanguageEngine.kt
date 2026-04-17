package com.pdrxcode.engine

class LanguageEngine(
    private val config: LanguageConfig
) {

    private val tokenizer = Tokenizer(config)

    fun analyze(code: String): List<Token> {
        return tokenizer.tokenize(code)
    }

    fun getColor(type: String): String {
        return config.colors[type] ?: "#FFFFFF"
    }
}