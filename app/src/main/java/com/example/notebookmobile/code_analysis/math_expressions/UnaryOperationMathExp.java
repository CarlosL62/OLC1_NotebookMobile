package com.example.notebookmobile.code_analysis.math_expressions;

import com.example.notebookmobile.code_analysis.expressions.DefineOperation;
import com.example.notebookmobile.code_analysis.expressions.Expression;
import com.example.notebookmobile.code_analysis.utils.Position;

import java.util.HashMap;
import java.util.List;

public class UnaryOperationMathExp extends MathExpression {

    private DefineOperation operation;
    private MathExpression mathExpression;
    private Position pos;

    public UnaryOperationMathExp(DefineOperation operation, MathExpression mathExpression, Position pos) {
        this.operation = operation;
        this.mathExpression = mathExpression;
        this.pos = pos;
    }

    @Override
    public void execute() {
        switch (operation){
            case PLUS:
                function.append("+");
                mathExpression.execute();
                break;
            case MINUS:
                function.append("-");
                mathExpression.execute();
                break;
            default:
                throw new AssertionError("Unknown operation: " + operation);
        }
    }

}
