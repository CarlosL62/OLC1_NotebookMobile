package com.example.notebookmobile.code_analysis;

import android.widget.TextView;

import com.example.notebookmobile.code_analysis.instructions.Instruction;

import java.io.StringReader;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class Analyzer {
    private TextView responseTextView;
    private StringBuilder terminal;
    private List<String> semanticErrors;
    private HashMap<String, Object> symbolsTable;

    public Analyzer(TextView responseTextView) {
        this.responseTextView = responseTextView;
    }

    public void execute(List<Instruction> instructions) {
        terminal = new StringBuilder();
        semanticErrors = new LinkedList<>();
        symbolsTable = new HashMap<>();
        for (Instruction instruction : instructions) {
            instruction.execute(symbolsTable, terminal, semanticErrors);
        }

    }

    public void executeCode(String code) {
        StringReader reader = new StringReader(code);
        CodeLexer lexer = new CodeLexer(reader);
        CodeParser parser = new CodeParser(lexer, this);


        try {
            parser.parse();
            if(lexer.getErrors().isEmpty() && parser.getSyntaxErrors().isEmpty() && semanticErrors.isEmpty()) {
                responseTextView.setText(terminal.toString());
            } else {
                for (String lexerError : lexer.getErrors()) {
                    responseTextView.append(lexerError + "\n");
                }
                for (String syntaxError : parser.getSyntaxErrors()) {
                    responseTextView.append(syntaxError + "\n");
                }
                for (String semanticError : semanticErrors) {
                    responseTextView.append(semanticError + "\n");
                }
            }
        } catch (Exception e) {
            responseTextView.setText(e.getMessage());
        }
    }
}
