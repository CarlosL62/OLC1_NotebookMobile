package com.example.notebookmobile.code_analysis.expressions;

import com.example.notebookmobile.code_analysis.utils.Position;

import java.util.HashMap;
import java.util.List;

public class UnaryOperation extends Expression {

    private DefineOperation operation;
    private Expression expression;
    private Position pos;

    public UnaryOperation(DefineOperation operation, Expression expression, Position pos) {
        this.operation = operation;
        this.expression = expression;
        this.pos = pos;
    }

    @Override
    public Object execute(HashMap<String, Object> symbolsTable, StringBuilder terminal, List<String> semanticErrors) {
        switch (operation){
            case PLUS:
                return +(float)expression.execute(symbolsTable, terminal, semanticErrors);
            case MINUS:
                return -(float)expression.execute(symbolsTable, terminal, semanticErrors);
            default:
                throw new AssertionError("Unknown operation: " + operation);
        }
    }

}
