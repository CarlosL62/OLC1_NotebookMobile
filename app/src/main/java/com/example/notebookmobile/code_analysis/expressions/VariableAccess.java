package com.example.notebookmobile.code_analysis.expressions;

import com.example.notebookmobile.code_analysis.utils.Position;

import java.util.HashMap;
import java.util.List;

public class VariableAccess extends Expression {

    private String name;
    private Position pos;

    public VariableAccess(String name, Position pos) {
        this.name = name;
        this.pos = pos;
    }

    @Override
    public Object execute(HashMap<String, Object> symbolsTable, StringBuilder terminal, List<String> semanticErrors) {
        if (!symbolsTable.containsKey(name)) {
            semanticErrors.add("No se encontro la variable " + name + " en la posición " + pos.getLine() + ":" + pos.getCol());
            return null;
        }
        return symbolsTable.get(name);
    }
}
