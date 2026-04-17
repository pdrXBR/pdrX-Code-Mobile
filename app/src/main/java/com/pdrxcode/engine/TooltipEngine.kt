package com.pdrxcode.engine

class TooltipEngine(
    private val engine: LanguageEngine
) {

    fun getTooltip(word: String): LanguageItem? {

        // 🔥 KEYWORDS
        engine.config.keywords[word]?.let { return it }

        // 🔥 FUNCTIONS
        engine.config.functions[word]?.let { return it }

        // 🔥 CHARACTERS (converte pra LanguageItem)
        engine.getCharacter(word)?.let {
            return LanguageItem(
                name = it.name,
                description = "Personagem do diálogo",
                syntax = "${it.name} \"fala\"",
                example = "${it.name} \"Olá!\""
            )
        }

        return null
    }
}