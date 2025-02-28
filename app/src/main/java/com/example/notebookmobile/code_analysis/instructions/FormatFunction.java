package com.example.notebookmobile.code_analysis.instructions;

import com.example.notebookmobile.code_analysis.utils.Position;
import java.util.HashMap;
import java.util.List;

public class FormatFunction extends Instruction {
    private final String mathExpression;

    public FormatFunction(String mathExpression, Position position) {
        this.mathExpression = mathExpression;
        this.position = position;
    }

    @Override
    public void execute(HashMap<String, Object> symbolsTable, StringBuilder terminal, List<String> semanticErrors) {
        String latexExpression = convertToLatex(mathExpression);

        // Agrega la expresión formateada al terminal (StringBuilder)
        terminal.append("LaTeX: ").append(latexExpression).append("\n");
    }

    private String convertToLatex(String expression) {
        // Reemplazos básicos para convertir operadores a LaTeX
        String latexExpr = expression.replace("^", "^{")
                .replace("*", "\\cdot ")
                .replace("(", "\\left(")
                .replace(")", "\\right)");

        // Manejo de fracciones
        latexExpr = replaceFractions(latexExpr);

        return "$" + latexExpr + "$"; // Devuelve la expresión delimitada por $
    }

    private String replaceFractions(String expression) {
        // x/(x*y) -> \frac{x}{x*y}
        expression = expression.replaceAll("(\\w+)\\s*/\\s*\\(([^)]+)\\)", "\\\\frac{\\1}{\\2}");

        // a/b -> \frac{a}{b}
        expression = expression.replaceAll("(\\w+)\\s*/\\s*(\\w+)", "\\\\frac{\\1}{\\2}");

        return expression;
    }
}
