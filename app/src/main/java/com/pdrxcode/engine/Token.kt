package com.pdrxcode.engine

data class Token(
    val value: String,
    val type: String,
    val start: Int,
    val end: Int
)