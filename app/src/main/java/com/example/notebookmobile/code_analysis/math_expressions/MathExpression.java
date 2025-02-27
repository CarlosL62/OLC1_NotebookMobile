package com.example.notebookmobile.code_analysis.math_expressions;

import androidx.annotation.OpenForTesting;

import java.util.HashMap;
import java.util.List;

public abstract class MathExpression {

    protected static StringBuilder function;

    public MathExpression() {
        function = new StringBuilder();
    }

    public static StringBuilder getFunction() {
        return function;
    }

    public abstract void execute();
}
