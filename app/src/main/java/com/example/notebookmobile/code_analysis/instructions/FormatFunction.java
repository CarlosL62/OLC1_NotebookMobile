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
        terminal.append("LaTeX: ").append(latexExpression).append("\n");
    }

    private String convertToLatex(String expression) {
        try {
            String formattedExpression = expression
                    .replaceAll("\\*", " \\cdot ")
                    .replaceAll("\\b(\\w+)\\s*\\^\\s*(\\d+)\\b", "$1^{ $2 }");

            // Convert divs to \frac{a}{b}
            formattedExpression = formattedExpression.replaceAll("(\\w+)\\s*/\\s*(\\w+)", "\\\\frac{$1}{$2}");

            return "$$" + formattedExpression + "$$";
        } catch (Exception e) {
            System.err.println("❌ Error al convertir a LaTeX: " + e.getMessage());
            return "$$\\text{Expresión inválida}$$";
        }
    }


}
