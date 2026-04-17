package com.pdrxcode.domain.model

data class CodeState(
    val text: String = "",
    val cursorPosition: Int = 0,
    val selectionStart: Int = 0,
    val selectionEnd: Int = 0,
    val language: String = "text"
)