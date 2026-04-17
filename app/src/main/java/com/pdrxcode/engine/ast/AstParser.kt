package com.pdrxcode.engine.ast

class AstParser {

    fun parse(tokens: List<Token>): AstNode.Root {

        val nodes = mutableListOf<AstNode>()

        var i = 0

        while (i < tokens.size) {

            val token = tokens[i]

            when (token.type) {

                "keyword" -> {
                    nodes.add(
                        AstNode.Statement(
                            type = token.value,
                            value = token.value,
                            line = 0
                        )
                    )
                }

                else -> {
                    nodes.add(
                        AstNode.Expression(token.value)
                    )
                }
            }

            i++
        }

        return AstNode.Root(nodes)
    }
}