package com.pdrxcode.engine

data class LanguageItem(
    val name: String,
    val description: String,
    val syntax: String,
    val example: String,
    val color: String? = null
)