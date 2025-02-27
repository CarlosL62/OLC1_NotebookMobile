package com.example.notebookmobile.code_analysis.math_expressions;

import com.example.notebookmobile.code_analysis.expressions.Expression;
import com.example.notebookmobile.code_analysis.utils.Position;

import java.util.HashMap;
import java.util.List;

public class VariableMathExp extends MathExpression {

    private String name;
    private Position pos;

    public VariableMathExp(String name, Position pos) {
        this.name = name;
        this.pos = pos;
    }

    @Override
    public void execute() {
        function.append(name);
    }
}
