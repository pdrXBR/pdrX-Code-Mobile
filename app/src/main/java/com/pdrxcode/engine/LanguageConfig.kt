package com.pdrxcode.engine

data class LanguageConfig(
    val language: String,
    val extends: String? = null,

    val patterns: Map<String, String> = emptyMap(),
    val colors: Map<String, String> = emptyMap(),

    val keywords: Map<String, LanguageItem> = emptyMap(),
    val functions: Map<String, LanguageItem> = emptyMap(),

    // 🔥 NOVO
    val characters: Map<String, LanguageItem> = emptyMap()
)