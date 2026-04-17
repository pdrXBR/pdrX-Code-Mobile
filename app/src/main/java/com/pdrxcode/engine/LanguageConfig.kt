package com.pdrxcode.engine

data class LanguageConfig(
    val language: String,
    val patterns: Map<String, String>,
    val keywords: Map<String, Any>,
    val functions: Map<String, Any>,
    val colors: Map<String, String>
)