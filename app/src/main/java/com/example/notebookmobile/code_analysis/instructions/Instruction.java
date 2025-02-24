package com.example.notebookmobile.code_analysis.instructions;
import com.example.notebookmobile.code_analysis.utils.Position;

import java.util.HashMap;
import java.util.List;

public abstract class Instruction {

    protected Position position;

    // Public abstract method void execute(Object SymbolTable, Object Terminal)
    public abstract void execute(HashMap<String, Object> symbolsTable, StringBuilder terminal, List<String> semanticErrors);
}
