package com.example.notebookmobile.code_analysis;

import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import ru.noties.jlatexmath.JLatexMathDrawable;

import com.example.notebookmobile.R;
import com.example.notebookmobile.code_analysis.instructions.Instruction;

import java.io.StringReader;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class Analyzer {
    private TextView responseTextView;
    private ImageView mathImageView;
    private StringBuilder terminal;
    private List<String> semanticErrors;
    private HashMap<String, Object> symbolsTable;

    public Analyzer(TextView responseTextView, ImageView mathImageView) {
        this.responseTextView = responseTextView;
        this.mathImageView = mathImageView;
        this.semanticErrors = new LinkedList<>();
    }

    public void execute(List<Instruction> instructions) {
        terminal = new StringBuilder();
        semanticErrors = new LinkedList<>();
        symbolsTable = new HashMap<>();

        for (Instruction instruction : instructions) {
            instruction.execute(symbolsTable, terminal, semanticErrors);
        }
    }

    public void executeCode(String code) {
        StringReader reader = new StringReader(code);
        CodeLexer lexer = new CodeLexer(reader);
        CodeParser parser = new CodeParser(lexer, this);

        try {
            parser.parse();

            if (lexer.getErrors().isEmpty() &&
                    (parser.getSyntaxErrors() != null && parser.getSyntaxErrors().isEmpty()) &&
                    (semanticErrors != null && semanticErrors.isEmpty())) {

                String result = terminal.toString().trim();
                System.out.println("✅ Resultado del análisis: " + result);

                if (result.startsWith("LaTeX: ")) {
                    String latexExpression = result.replace("LaTeX: ", "").trim();
                    System.out.println("🎯 Expresión LaTeX final: " + latexExpression);

                    if (!latexExpression.isEmpty()) {
                        // Limpiar delimitadores extra y mejorar la expresión
                        final String finalLatexExpression = sanitizeLatexExpression(latexExpression);

                        // Ejecutar en el hilo principal con un Handler
                        new Handler(Looper.getMainLooper()).post(() -> renderLatex(finalLatexExpression));

                    } else {
                        responseTextView.setText("[⚠️ Expresión LaTeX vacía]");
                    }
                } else {
                    responseTextView.setVisibility(View.VISIBLE);
                    mathImageView.setVisibility(View.GONE);
                    responseTextView.setText(result);
                }

            } else {
                responseTextView.setVisibility(View.VISIBLE);
                mathImageView.setVisibility(View.GONE);
                responseTextView.setText("[❌ Errores detectados]\n");

                for (String lexerError : lexer.getErrors()) {
                    responseTextView.append(lexerError + "\n");
                }
                for (String syntaxError : parser.getSyntaxErrors()) {
                    responseTextView.append(syntaxError + "\n");
                }
                for (String semanticError : semanticErrors) {
                    responseTextView.append(semanticError + "\n");
                }
            }
        } catch (Exception e) {
            responseTextView.setVisibility(View.VISIBLE);
            mathImageView.setVisibility(View.GONE);
            responseTextView.setText("[💥 Error al analizar el código]: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Método para limpiar delimitadores innecesarios en expresiones LaTeX.
     * @param expression La expresión LaTeX procesada.
     * @return Expresión sin delimitadores adicionales.
     */
    private String sanitizeLatexExpression(String expression) {
        if (expression.startsWith("$") && expression.endsWith("$")) {
            expression = expression.substring(1, expression.length() - 1).trim();
        }
        if (expression.startsWith("\\(") && expression.endsWith("\\)")) {
            expression = expression.substring(2, expression.length() - 2).trim();
        }

        // Agregar \displaystyle si parece ser una ecuación compleja
        if (expression.contains("\\frac") || expression.contains("\\sum") || expression.contains("\\int")) {
            expression = "\\displaystyle " + expression;
        }

        return expression;
    }

    /**
     * Renderiza una expresión LaTeX en mathImageView.
     * @param latexExpression La expresión LaTeX final.
     */
    private void renderLatex(String latexExpression) {
        try {
            System.out.println("🔍 Expresión enviada a JLaTeXMath: " + latexExpression);

            JLatexMathDrawable drawable = JLatexMathDrawable.builder(latexExpression)
                    .textSize(100)  // Ajustar tamaño para mejor legibilidad
                    .padding(30)   // Agregar espacio alrededor
                    .build();

            if (drawable == null) {
                System.err.println("❌ ERROR: JLaTeXMathDrawable es NULL");
                responseTextView.setVisibility(View.VISIBLE);
                mathImageView.setVisibility(View.GONE);
                responseTextView.setText("⚠️ Error al generar la imagen LaTeX.");
            } else {
                System.out.println("✅ JLaTeXMathDrawable generado correctamente");

                responseTextView.setVisibility(View.GONE);
                mathImageView.setVisibility(View.VISIBLE);
                mathImageView.setImageDrawable(drawable);

                // 🔴 Forzar redibujado
                mathImageView.requestLayout();
                mathImageView.invalidate();
            }
        } catch (Exception e) {
            System.err.println("❌ ERROR al renderizar LaTeX: " + e.getMessage());
            responseTextView.setVisibility(View.VISIBLE);
            mathImageView.setVisibility(View.GONE);
            responseTextView.setText("⚠️ No se pudo generar la imagen LaTeX.\nRevisa la expresión.");
            e.printStackTrace();
        }
    }
}
