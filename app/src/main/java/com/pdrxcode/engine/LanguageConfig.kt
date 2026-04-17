package com.pdrxcode.engine

data class LanguageConfig(
    val language: String,
    val patterns: Map<String, String>,
    val keywords: Map<String, Tooltip>,
    val functions: Map<String, Tooltip>,
    val colors: Map<String, String>
)