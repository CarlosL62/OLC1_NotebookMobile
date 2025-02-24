package com.example.notebookmobile.code_analysis.instructions;

import com.example.notebookmobile.code_analysis.expressions.Expression;
import com.example.notebookmobile.code_analysis.utils.Position;

import java.util.HashMap;
import java.util.List;

public class Assignation extends Instruction {
    private String variable;
    private Expression expression;

    public Assignation(String variable, Expression expression, Position pos) {
        this.variable = variable;
        this.expression = expression;
        this.position = pos;
    }

    @Override
    public void execute(HashMap<String, Object> symbolsTable, StringBuilder terminal, List<String> semanticErrors) {
        symbolsTable.put(variable, expression.execute(symbolsTable, terminal, semanticErrors));
    }
}
