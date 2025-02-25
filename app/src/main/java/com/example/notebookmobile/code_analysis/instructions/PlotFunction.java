package com.example.notebookmobile.code_analysis.instructions;

import com.example.notebookmobile.code_analysis.expressions.Expression;
import com.example.notebookmobile.code_analysis.expressions.Operation;

import java.util.HashMap;
import java.util.List;

public class PlotFunction extends Instruction {

    private String mathFunction;
    private Float left;
    private Float right;

    public PlotFunction(String mathFunction, Float left, Float right) {
        this.mathFunction = mathFunction;
        this.left = left;
        this.right = right;
    }

    public float executeMathFunction(HashMap<String, Object> symbolsTable, StringBuilder terminal, List<String> semanticErrors, float x) {
        mathFunction.replace("x", Float.toString(x));
        return 0f;
        //TODO: Implement the math function
    }

    @Override
    public void execute(HashMap<String, Object> symbolsTable, StringBuilder terminal, List<String> semanticErrors) {
        terminal.append("--------------------\n");
        terminal.append("x          f(x)\n");
        for (float i = left; i <= right; i ++) {
            terminal.append(i + "          " + executeMathFunction(symbolsTable, terminal, semanticErrors, i) + "\n");
        }

    }
}
