package com.pdrxcode.ui.components

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pdrxcode.ui.theme.VSCodeBackground
import com.pdrxcode.ui.theme.VSCodeTextPrimary

/**
 * Editor de código reutilizável.
 * 
 * @param code Texto atual do editor.
 * @param onCodeChange Callback quando o texto é alterado.
 * @param modifier Modificador opcional.
 * @param readOnly Define se o editor é somente leitura (útil para preview).
 */
@Composable
fun CodeEditor(
    code: String,
    onCodeChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    readOnly: Boolean = false
) {
    // Estado interno do TextFieldValue para controlar seleção e cursor
    var textFieldValue by remember { mutableStateOf(TextFieldValue(code)) }

    // Sincroniza alterações externas do código com o estado interno
    if (textFieldValue.text != code) {
        textFieldValue = textFieldValue.copy(text = code)
    }

    // Cores de seleção customizadas (estilo VS Code)
    val customTextSelectionColors = TextSelectionColors(
        handleColor = MaterialTheme.colorScheme.primary,
        backgroundColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.4f)
    )

    // Scroll vertical para lidar com muitas linhas
    val scrollState = rememberScrollState()

    CompositionLocalProvider(LocalTextSelectionColors provides customTextSelectionColors) {
        Box(
            modifier = modifier
                .background(VSCodeBackground)
                .verticalScroll(scrollState)
        ) {
            BasicTextField(
                value = textFieldValue,
                onValueChange = { newValue ->
                    textFieldValue = newValue
                    onCodeChange(newValue.text)
                },
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp),
                textStyle = TextStyle(
                    fontFamily = FontFamily.Monospace,
                    fontSize = 14.sp,
                    color = VSCodeTextPrimary,
                    background = Color.Transparent
                ),
                cursorBrush = SolidColor(MaterialTheme.colorScheme.primary),
                visualTransformation = VisualTransformation.None, // Highlight virá depois
                readOnly = readOnly,
                decorationBox = { innerTextField ->
                    // Suporte a scroll horizontal: usamos um Box com scroll horizontal habilitado?
                    // O BasicTextField por padrão não suporta scroll horizontal nativo.
                    // Para isso, vamos envolver o innerTextField em um Box com horizontalScroll
                    androidx.compose.foundation.horizontalScroll(rememberScrollState())
                    Box(modifier = Modifier.fillMaxSize()) {
                        innerTextField()
                    }
                }
            )
        }
    }
}