package com.pdrxcode.engine

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.*
import androidx.compose.ui.text.buildAnnotatedString

class Highlighter(
    private val engine: LanguageEngine
) {

    fun highlight(code: String): AnnotatedString {
        val tokens = engine.analyze(code)
        val defaultColor = Color(0xFFD4D4D4)

        return buildAnnotatedString {
            var lastIndex = 0

            tokens.forEach { token ->

                if (token.start > lastIndex) {
                    pushStyle(SpanStyle(color = defaultColor))
                    append(code.substring(lastIndex, token.start))
                    pop()
                }

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

                lastIndex = token.end
            }

            if (lastIndex < code.length) {
                pushStyle(SpanStyle(color = defaultColor))
                append(code.substring(lastIndex))
                pop()
            }
        }
    }
}