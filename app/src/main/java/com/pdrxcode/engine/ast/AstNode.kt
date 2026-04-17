package com.pdrxcode.engine.ast

sealed class AstNode {
    data class Root(val children: List<AstNode>) : AstNode()

    data class Statement(
        val type: String,
        val value: String,
        val line: Int
    ) : AstNode()

    data class Expression(
        val value: String
    ) : AstNode()
}