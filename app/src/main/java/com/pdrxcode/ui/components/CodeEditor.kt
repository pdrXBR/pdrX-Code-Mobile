package com.pdrxcode.ui.components

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.*
import androidx.compose.foundation.text.selection.*
import androidx.compose.material3.MaterialTheme
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
import com.pdrxcode.engine.AdvancedEngine
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

    // =========================
    // 🔥 ENGINE CENTRAL (AST)
    // =========================
    val engine = remember(languageFile) {
        val config = LanguageLoader.load(context, languageFile)
        LanguageEngine(config)
    }

    val advancedEngine = remember {
        AdvancedEngine()
    }

    val highlighter = remember { Highlighter(engine) }

    var textFieldValue by remember { mutableStateOf(TextFieldValue(code)) }
    var tooltipWord by remember { mutableStateOf<String?>(null) }

    if (textFieldValue.text != code) {
        textFieldValue = textFieldValue.copy(text = code)
    }

    // =========================
    // ⚡ AST (FONTE DE TUDO)
    // =========================
    val ast = remember(textFieldValue.text) {
        advancedEngine.getAst(textFieldValue.text)
    }

    // =========================
    // 🎨 HIGHLIGHT (baseado no AST futuro)
    // =========================
    val highlightedText = remember(ast) {
        highlighter.highlight(textFieldValue.text)
    }

    // =========================
    // ⚡ AUTOCOMPLETE (AST READY)
    // =========================
    val suggestions = remember(ast, textFieldValue.selection.start) {
        // depois você troca pra AST autocomplete real
        advancedEngine.getTokens(textFieldValue.text)
            .map { it.value }
            .distinct()
            .take(6)
    }

    // =========================
    // 💬 TOOLTIP (AST READY)
    // =========================
    val tooltipData = remember(tooltipWord, ast) {
        tooltipWord?.let {
            // depois vira AST lookup real
            null
        }
    }

    // =========================
    // ⚠ LINTER (AST BASE)
    // =========================
    val lintErrors = remember(ast) {
        // placeholder futuro AST linter
        emptyList<String>()
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
                                textFieldValue.selection.start
                            )
                        }
                    )
                }
        ) {

            // =========================
            // 🎨 HIGHLIGHT LAYER
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
            // ⌨ INPUT LAYER (CURSOR REAL)
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
            if (suggestions.isNotEmpty()) {

                Column(
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(start = 24.dp, top = 40.dp)
                        .background(Color(0xFF1E1E1E))
                        .width(220.dp)
                ) {
                    suggestions.forEach { item ->
                        Text(
                            text = item,
                            color = Color.White,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {

                                    val newText = textFieldValue.text +
                                            if (textFieldValue.text.endsWith(" ")) item else " $item"

                                    textFieldValue = TextFieldValue(
                                        newText,
                                        TextRange(newText.length)
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
            tooltipData?.let {
                Column(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(12.dp)
                        .background(Color(0xFF252526))
                        .padding(10.dp)
                ) {
                    Text(it, color = Color.White)
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
                lintErrors.take(3).forEach {
                    Text(
                        text = it,
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