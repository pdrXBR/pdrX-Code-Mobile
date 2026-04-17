package com.pdrxcode.engine

import com.pdrxcode.engine.ast.*

class AdvancedEngine {

    private val tokenizer = Tokenizer()
    private val parser = AstParser()
    private val cache = IncrementalAstCache()

    fun getAst(code: String): AstNode.Root {
        return cache.getAst(code, tokenizer, parser)
    }

    fun getTokens(code: String): List<Token> {
        return tokenizer.tokenize(code)
    }
}