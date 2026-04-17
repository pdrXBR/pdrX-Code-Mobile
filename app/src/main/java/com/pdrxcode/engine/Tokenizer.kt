class Tokenizer(
    private val config: LanguageConfig
) {

    fun tokenize(code: String): List<Token> {
        val tokens = mutableListOf<Token>()

        config.patterns.forEach { (type, regex) ->
            Regex(regex).findAll(code).forEach {
                tokens.add(
                    Token(
                        value = it.value,
                        type = type,
                        start = it.range.first,
                        end = it.range.last
                    )
                )
            }
        }

        return tokens.sortedBy { it.start }
    }
}