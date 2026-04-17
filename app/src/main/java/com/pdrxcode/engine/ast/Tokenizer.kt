package com.pdrxcode.engine.ast

class Tokenizer {

    fun tokenize(code: String): List<Token> {

        val tokens = mutableListOf<Token>()
        val regex = Regex("\\w+|\".*?\"|'.*?'|\\d+|[{}()=+\\-*/]")

        regex.findAll(code).forEach {

            val value = it.value

            val type = when {
                value.matches(Regex("\\b(if|for|while|def|class|return)\\b")) -> "keyword"
                value.matches(Regex("\\d+")) -> "number"
                value.startsWith("\"") || value.startsWith("'") -> "string"
                else -> "identifier"
            }

            tokens.add(
                Token(
                    type,
                    value,
                    it.range.first,
                    it.range.last
                )
            )
        }

        return tokens
    }
}