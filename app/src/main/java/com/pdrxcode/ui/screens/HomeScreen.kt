package com.pdrxcode.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.pdrxcode.ui.components.CodeEditor

@Composable
fun HomeScreen() {
    // Estado do código gerenciado na tela (futuramente via ViewModel)
    var code by remember { mutableStateOf("// Digite seu código aqui\n") }

    Column(modifier = Modifier.fillMaxSize()) {
        // Pequeno cabeçalho indicando a linguagem (placeholder)
        Text(
            text = "Editor (plain text)",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
        )
        // Editor ocupando o resto da tela
        CodeEditor(
            code = code,
            onCodeChange = { newCode -> code = newCode },
            modifier = Modifier
                .fillMaxSize()
                .weight(1f)
        )
    }
}