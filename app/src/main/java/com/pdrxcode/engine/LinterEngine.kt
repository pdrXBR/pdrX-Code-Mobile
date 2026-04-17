package com.pdrxcode.engine

data class LintError(
    val line: Int,
    val message: String
)

class LinterEngine {

    fun analyze(code: String): List<LintError> {

        val lines = code.split("\n")
        val errors = mutableListOf<LintError>()

        lines.forEachIndexed { index, line ->

            val trimmed = line.trim()

            // 🔥 regra 1: if/for/while precisam de ":"
            if (trimmed.startsWith("if")
                || trimmed.startsWith("for")
                || trimmed.startsWith("while")
                || trimmed.startsWith("def")
            ) {
                if (!trimmed.endsWith(":")) {
                    errors.add(
                        LintError(index + 1, "Falta ':' no final")
                    )
                }
            }

            // 🔥 regra 2: parênteses simples
            if (line.count { it == '(' } != line.count { it == ')' }) {
                errors.add(
                    LintError(index + 1, "Parênteses não fechados")
                )
            }

            // 🔥 regra 3: aspas
            val quotes = line.count { it == '"' }
            if (quotes % 2 != 0) {
                errors.add(
                    LintError(index + 1, "Aspas não fechadas")
                )
            }
        }

        return errors
    }
}