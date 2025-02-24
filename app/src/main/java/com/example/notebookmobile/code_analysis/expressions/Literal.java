package com.example.notebookmobile.code_analysis.expressions;

import java.util.HashMap;
import java.util.List;

public class Literal extends Expression {

    private Object value;

    public Literal(Object value) {
        this.value = value;
    }

    @Override
    public Object execute(HashMap<String, Object> symbolsTable, StringBuilder terminal, List<String> semanticErrors) {
        return value;
    }

}
