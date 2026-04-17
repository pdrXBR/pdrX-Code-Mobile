package com.pdrxcode.engine

class Tokenizer(
    private val config: LanguageConfig
) {

    fun tokenize(code: String): List<Token> {

        val tokens = mutableListOf<Token>()

        config.patterns.forEach { (type, pattern) ->

            val regex = Regex(pattern)

            regex.findAll(code).forEach { match ->

                tokens.add(
                    Token(
                        type = type,
                        value = match.value,
                        start = match.range.first,
                        end = match.range.last + 1
                    )
                )
            }
        }

        // 🔥 IMPORTANTE: ordena tokens
        return tokens.sortedBy { it.start }
    }
}