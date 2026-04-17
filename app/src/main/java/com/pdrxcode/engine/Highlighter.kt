package com.pdrxcode.engine

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString

class Highlighter(
    private val engine: LanguageEngine
) {

    fun highlight(code: String): AnnotatedString {
        val tokens = engine.analyze(code)

        return buildAnnotatedString {
            var lastIndex = 0

            tokens.forEach { token ->

                // texto entre tokens (normal)
                if (token.start > lastIndex) {
                    append(code.substring(lastIndex, token.start))
                }

                // cor do token
                val colorHex = engine.getColor(token.type)
                val color = Color(android.graphics.Color.parseColor(colorHex))

                pushStyle(
                    SpanStyle(color = color)
                )

                append(token.value)
                pop()

                lastIndex = token.end + 1
            }

            // resto do texto
            if (lastIndex < code.length) {
                append(code.substring(lastIndex))
            }
        }
    }
}