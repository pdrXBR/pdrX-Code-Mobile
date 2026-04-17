package com.pdrxcode.engine

data class LanguageConfig(
    val language: String,
    val patterns: Map<String, String>,
    val keywords: Map<String, LanguageItem>,
    val functions: Map<String, LanguageItem>,
    val colors: Map<String, String>,
    val characters: Map<String, Character>
)