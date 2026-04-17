package com.pdrxcode.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.selection.*
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.*
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pdrxcode.engine.*
import com.pdrxcode.ui.theme.VSCodeBackground

@Composable
fun CodeEditor(
    code: String,
    onCodeChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    languageFile: String = "python.json",
    readOnly: Boolean = false
) {

    val context = LocalContext.current

    val engine = remember(languageFile) {
        val config = LanguageLoader.load(context, languageFile)
        LanguageEngine(config)
    }

    val highlighter = remember { Highlighter(engine) }
    val autocomplete = remember { AutocompleteEngine(engine) }
    val tooltipEngine = remember { TooltipEngine(engine) }
    val linter = remember { LinterEngine() }

    var textFieldValue by remember { mutableStateOf(TextFieldValue(code)) }
    var tooltipWord by remember { mutableStateOf<String?>(null) }

    if (textFieldValue.text != code) {
        textFieldValue = textFieldValue.copy(text = code)
    }

    val cursor = textFieldValue.selection.start

    // =========================
    // 🔥 PREFIXO INTELIGENTE
    // =========================
    val prefix = textFieldValue.text
        .take(cursor)
        .takeLastWhile {
            it.isLetterOrDigit() || it == '_' || it == '.'
        }

    val showAutocomplete = prefix.isNotEmpty()

    // =========================
    // 🎨 HIGHLIGHT
    // =========================
    val highlightedText = remember(textFieldValue.text) {
        highlighter.highlight(textFieldValue.text)
    }

    // =========================
    // ⚡ AUTOCOMPLETE
    // =========================
    val suggestions = remember(textFieldValue.text, cursor) {
        if (!showAutocomplete) emptyList()
        else autocomplete.getSuggestions(textFieldValue.text, cursor)
    }

    // =========================
    // 💬 TOOLTIP
    // =========================
    val tooltipData = remember(tooltipWord) {
        tooltipWord?.let { tooltipEngine.getTooltip(it) }
    }

    // =========================
    // ⚠ LINTER
    // =========================
    val lintErrors = remember(textFieldValue.text) {
        linter.analyze(textFieldValue.text)
    }

    val selectionColors = TextSelectionColors(
        handleColor = MaterialTheme.colorScheme.primary,
        backgroundColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.4f)
    )

    CompositionLocalProvider(LocalTextSelectionColors provides selectionColors) {

        Box(
            modifier = modifier
                .background(VSCodeBackground)
                .pointerInput(Unit) {
                    detectTapGestures(
                        onLongPress = {
                            tooltipWord = getWordAt(
                                textFieldValue.text,
                                cursor
                            )
                        }
                    )
                }
        ) {

            // =========================
            // 🎨 HIGHLIGHT
            // =========================
            BasicText(
                text = highlightedText,
                style = TextStyle(
                    fontFamily = FontFamily.Monospace,
                    fontSize = 14.sp,
                    lineHeight = 20.sp
                ),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp)
            )

            // =========================
            // ⌨ INPUT
            // =========================
            BasicTextField(
                value = textFieldValue,
                onValueChange = {
                    textFieldValue = it
                    onCodeChange(it.text)
                },
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp),

                textStyle = TextStyle(
                    fontFamily = FontFamily.Monospace,
                    fontSize = 14.sp,
                    lineHeight = 20.sp,
                    color = Color.Transparent
                ),

                cursorBrush = SolidColor(MaterialTheme.colorScheme.primary),
                readOnly = readOnly
            )

            // =========================
            // ⚡ AUTOCOMPLETE UI
            // =========================
            if (showAutocomplete && suggestions.isNotEmpty()) {

                Column(
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .offset(
                            x = 8.dp,
                            y = ((cursor / 25) * 20).dp // simula posição
                        )
                        .background(Color(0xFF1E1E1E))
                        .width(220.dp)
                ) {

                    suggestions.take(6).forEach { item ->

                        Text(
                            text = item,
                            color = Color.White,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {

                                    val newText = textFieldValue.text.replaceRange(
                                        cursor - prefix.length,
                                        cursor,
                                        item
                                    )

                                    val newCursor =
                                        cursor - prefix.length + item.length

                                    textFieldValue = TextFieldValue(
                                        newText,
                                        TextRange(newCursor)
                                    )

                                    onCodeChange(newText)
                                }
                                .padding(8.dp)
                        )
                    }
                }
            }

            // =========================
            // 💬 TOOLTIP UI
            // =========================
            tooltipData?.let { tip ->

                Column(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(12.dp)
                        .background(Color(0xFF252526))
                        .padding(10.dp)
                ) {
                    Text(text = tip.name, color = Color.White)
                    Text(text = tip.description, color = Color.LightGray)
                    Text(text = tip.syntax, color = Color.Gray)
                    Text(text = tip.example, color = Color.Green)
                }
            }

            // =========================
            // ⚠ LINTER UI
            // =========================
            Column(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(8.dp)
            ) {
                lintErrors.take(3).forEach { err ->
                    Text(
                        text = "Linha ${err.line}: ${err.message}",
                        color = Color.Red,
                        fontSize = 12.sp
                    )
                }
            }
        }
    }
}

// =========================
// 🔧 UTIL
// =========================
private fun getWordAt(text: String, index: Int): String {
    if (text.isEmpty()) return ""

    val safeIndex = index.coerceIn(0, text.length)

    val left = text.take(safeIndex).takeLastWhile {
        it.isLetterOrDigit() || it == '_'
    }

    val right = text.drop(safeIndex).takeWhile {
        it.isLetterOrDigit() || it == '_'
    }

    return left + right
}