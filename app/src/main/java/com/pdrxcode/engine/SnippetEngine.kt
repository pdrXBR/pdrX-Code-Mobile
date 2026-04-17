package com.pdrxcode.engine

class SnippetEngine {

    private val snippets = mapOf(

        // 🔥 RENPY
        "label" to "label nome:\n    scene bg classroom\n    show m happy\n\n    m \"...\"\n\n    return",

        "scene" to "scene bg ",
        "show" to "show ",
        "menu" to "menu:\n    \"Escolha\":\n        \"Opção 1\":\n            jump rota1",

        // 🐍 PYTHON
        "for" to "for i in range(10):\n    pass",
        "if" to "if condicao:\n    pass",
        "def" to "def nome():\n    pass"
    )

    fun getSnippet(word: String): String? {
        return snippets[word]
    }
}