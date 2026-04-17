package com.pdrxcode.engine

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.*
import androidx.compose.ui.text.buildAnnotatedString

class Highlighter(
    private val engine: LanguageEngine
) {

    private val inlineVarRegex = Regex("\\[([a-zA-Z_][a-zA-Z0-9_]*)\\]")

    fun highlight(code: String): AnnotatedString {
        val tokens = engine.analyze(code)
        val defaultColor = Color(0xFFD4D4D4)

        return buildAnnotatedString {
            var lastIndex = 0

            tokens.forEach { token ->

                // texto normal
                if (token.start > lastIndex) {
                    pushStyle(SpanStyle(color = defaultColor))
                    append(code.substring(lastIndex, token.start))
                    pop()
                }

                if (token.type == "string") {

                    val stringText = token.value
                    val stringColor = Color(android.graphics.Color.parseColor(engine.getColor("string")))
                    val varColor = Color(android.graphics.Color.parseColor(engine.getColor("variable_inline")))

                    var innerIndex = 0

                    inlineVarRegex.findAll(stringText).forEach { match ->

                        val start = match.range.first
                        val end = match.range.last + 1

                        // texto antes da variável
                        if (start > innerIndex) {
                            pushStyle(SpanStyle(color = stringColor))
                            append(stringText.substring(innerIndex, start))
                            pop()
                        }

                        // variável [x]
                        pushStyle(SpanStyle(color = varColor))
                        append(match.value)
                        pop()

                        innerIndex = end
                    }

                    // resto da string
                    if (innerIndex < stringText.length) {
                        pushStyle(SpanStyle(color = stringColor))
                        append(stringText.substring(innerIndex))
                        pop()
                    }

                } else {

                    val colorHex = if (token.type == "character") {
                        engine.getCharacter(token.value)?.color
                            ?: engine.getColor("character")
                    } else {
                        engine.getColor(token.type)
                    }

                    val color = Color(android.graphics.Color.parseColor(colorHex))

                    pushStyle(SpanStyle(color = color))
                    append(token.value)
                    pop()
                }

                lastIndex = token.end
            }

            // resto
            if (lastIndex < code.length) {
                pushStyle(SpanStyle(color = defaultColor))
                append(code.substring(lastIndex))
                pop()
            }
        }
    }
}