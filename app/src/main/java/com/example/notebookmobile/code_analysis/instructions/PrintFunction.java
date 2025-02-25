package com.example.notebookmobile.code_analysis.instructions;

import com.example.notebookmobile.code_analysis.expressions.Expression;
import com.example.notebookmobile.code_analysis.utils.Position;

import java.util.HashMap;
import java.util.List;

public class PrintFunction extends Instruction {
    private Expression value;
    private StringBuilder terminal;

    public void setTerminal(StringBuilder terminal) {
        this.terminal = terminal;
    }

    public PrintFunction(Expression value,  Position pos) {
        this.value = value;
        this.position = pos;
    }

    @Override
    public void execute(HashMap<String, Object> symbolsTable, StringBuilder terminal, List<String> semanticErrors) {
        terminal.append(value.execute(symbolsTable, terminal, semanticErrors));
        terminal.append("\n");
    }
}
