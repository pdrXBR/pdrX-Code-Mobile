package com.pdrxcode.engine.ast

class IncrementalAstCache {

    private var lastCode: String = ""
    private var lastAst: AstNode.Root? = null

    fun getAst(
        code: String,
        tokenizer: Tokenizer,
        parser: AstParser
    ): AstNode.Root {

        if (code == lastCode && lastAst != null) {
            return lastAst!!
        }

        val tokens = tokenizer.tokenize(code)
        val ast = parser.parse(tokens)

        lastCode = code
        lastAst = ast

        return ast
    }
}