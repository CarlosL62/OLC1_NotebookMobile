package com.example.notebookmobile.code_analysis.math_expressions;

import com.example.notebookmobile.code_analysis.expressions.DefineOperation;
import com.example.notebookmobile.code_analysis.expressions.Expression;
import com.example.notebookmobile.code_analysis.utils.Position;

import java.util.HashMap;
import java.util.List;

public class OperationMathExp extends MathExpression {

    private DefineOperation defOperation;
    private MathExpression left;
    private MathExpression right;
    private Position pos;

    public OperationMathExp(DefineOperation defOperation, MathExpression left, MathExpression right, Position pos) {
        this.defOperation = defOperation;
        this.left = left;
        this.right = right;
        this.pos = pos;
    }

    @Override
    public void execute() {
        switch (defOperation){
            case PLUS:
                left.execute();
                function.append("+");
                right.execute();
                break;
            case MINUS:
                left.execute();
                function.append("-");
                right.execute();
                break;
            case TIMES:
                left.execute();
                function.append("*");
                right.execute();
                break;
            case DIV:
                left.execute();
                function.append("/");
                right.execute();
                break;
            case POWER:
                left.execute();
                function.append("^");
                right.execute();
                break;
            default:
                throw new AssertionError("Unknown operation: " + defOperation);
        }
    }
}
