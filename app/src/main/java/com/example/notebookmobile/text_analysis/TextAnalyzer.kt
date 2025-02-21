package com.example.notebookmobile.text_analysis

import java.io.StringReader

class TextAnalyzer {
    fun analyze(input: String): String {
        return try {
            // Crear el lexer a partir del texto ingresado
            val lexer = TextLexer(StringReader(input))

            // Crear el parser usando el lexer
            val parser = TextParser(lexer)

            // Ejecutar el análisis sintáctico
            val result = parser.parse()

            // Devolver el resultado como un String
            "Análisis exitoso: $result"
        } catch (e: Exception) {
            // Manejar errores léxicos o sintácticos
            "Error: ${e.message}"
        }
    }
}