class LanguageEngine(
    private var config: LanguageConfig
) {

    private val tokenizer = Tokenizer(config)

    fun setLanguage(newConfig: LanguageConfig) {
        config = newConfig
    }

    fun analyze(code: String): List<Token> {
        return tokenizer.tokenize(code)
    }

    fun getSuggestions(prefix: String): List<String> {
        return (config.keywords.keys + config.functions.keys)
            .filter { it.startsWith(prefix) }
    }

    fun getTooltip(word: String): Tooltip? {
        return config.keywords[word]
            ?: config.functions[word]
    }

    fun getColor(type: String): String {
        return config.colors[type] ?: "#FFFFFF"
    }
}