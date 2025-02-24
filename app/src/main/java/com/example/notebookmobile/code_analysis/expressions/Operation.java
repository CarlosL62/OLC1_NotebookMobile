package com.example.notebookmobile.code_analysis.expressions;

import com.example.notebookmobile.code_analysis.utils.Position;

import java.util.HashMap;
import java.util.List;

public class Operation extends Expression {

    private DefineOperation defOperation;
    private Expression left;
    private Expression right;
    private Position pos;

    public Operation(DefineOperation defOperation, Expression left, Expression right, Position pos) {
        this.defOperation = defOperation;
        this.left = left;
        this.right = right;
        this.pos = pos;
    }

    @Override
    public Object execute(HashMap<String, Object> symbolsTable, StringBuilder terminal, List<String> semanticErrors) {
        switch (defOperation){
            case PLUS:
                return (float)left.execute(symbolsTable, terminal, semanticErrors) + (float)right.execute(symbolsTable, terminal, semanticErrors);
            case MINUS:
                return (float)left.execute(symbolsTable, terminal, semanticErrors) - (float)right.execute(symbolsTable, terminal, semanticErrors);
            case TIMES:
                return (float)left.execute(symbolsTable, terminal, semanticErrors) * (float)right.execute(symbolsTable, terminal, semanticErrors);
            case DIV:
                return (float)left.execute(symbolsTable, terminal, semanticErrors) / (float)right.execute(symbolsTable, terminal, semanticErrors);
            case POWER:
                return (float)Math.pow((float)left.execute(symbolsTable, terminal, semanticErrors), (float)right.execute(symbolsTable, terminal, semanticErrors));
            default:
                throw new AssertionError("Unknown operation: " + defOperation);
        }
    }
}
