package com.example.notebookmobile.code_analysis.instructions;

import com.example.notebookmobile.code_analysis.utils.Position;

import net.objecthunter.exp4j.Expression;

import java.util.HashMap;
import java.util.List;

public class PlotFunction extends Instruction {

    private String mathFunction;
    private Float left;
    private Float right;
    private Position pos;

    public PlotFunction(String mathFunction, Float left, Float right, Position pos) {
        this.mathFunction = mathFunction;
        this.left = left;
        this.right = right;
        this.pos = pos;
    }

    public float executeMathFunction(StringBuilder terminal, float x) {
        try {
            Expression expression = new net.objecthunter.exp4j.ExpressionBuilder(mathFunction)
                    .variables("x") // Define x as a variable
                    .build()
                    .setVariable("x", x); // Assign x the value of the current iteration

            return (float) expression.evaluate();
        } catch (Exception e) {
            terminal.append("Error evaluando la función: ").append(e.getMessage()).append("\n");
            return Float.NaN;
        }
    }

    @Override
    public void execute(HashMap<String, Object> symbolsTable, StringBuilder terminal, List<String> semanticErrors) {
        terminal.append("--------------------\n");
        terminal.append("x          f(x)\n");
        for (float i = left; i <= right; i ++) {
            terminal.append(i + "          " + executeMathFunction(terminal, i) + "\n");
        }
        terminal.append("--------------------\n");
    }
}
