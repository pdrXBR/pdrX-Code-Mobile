package com.pdrxcode.engine.ast

data class Token(
    val type: String,
    val value: String,
    val start: Int,
    val end: Int
)