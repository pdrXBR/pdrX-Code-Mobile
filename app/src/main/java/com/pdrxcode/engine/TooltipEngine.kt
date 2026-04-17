class TooltipEngine(
    private val engine: LanguageEngine
) {

    fun getTooltip(word: String): Tooltip? {

        val data = engine.config.keywords[word] ?: return null

        return Tooltip(
            name = word,
            description = data["description"] as? String ?: "",
            syntax = data["syntax"] as? String ?: "",
            example = data["example"] as? String ?: ""
        )
    }
}