package com.example.notebookmobile.code_analysis.math_expressions;

import java.util.HashMap;
import java.util.List;

public class LiteralMathExp extends MathExpression {

    private String value;

    public LiteralMathExp(Object value) {
        this.value = (String) value;
    }

    @Override
    public void execute() {
        function.append(value);
    }

}
