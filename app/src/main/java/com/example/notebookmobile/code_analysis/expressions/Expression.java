package com.example.notebookmobile.code_analysis.expressions;

import java.util.HashMap;
import java.util.List;

public abstract class Expression {

    public abstract Object execute(HashMap<String, Object> symbolsTable, StringBuilder terminal, List<String> semanticErrors);
}
