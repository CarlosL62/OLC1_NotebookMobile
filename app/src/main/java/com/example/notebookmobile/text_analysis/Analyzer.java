package com.example.notebookmobile.text_analysis;

import android.widget.TextView;

import com.example.notebookmobile.text_analysis.elements.Element;
import com.example.notebookmobile.text_analysis.elements.OrderedList;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

public class Analyzer {
    private TextView responseTextView;
    private List<String> syntaxErrors;
    private OrderedList orderedList;

    public Analyzer(TextView responseTextView) {
        this.responseTextView = responseTextView;
        this.syntaxErrors = new ArrayList<>();
    }

    public void execute(List<Element> elements) {
        if (responseTextView == null) return;
        responseTextView.setText("");
        for (Element element : elements) {
            element.execute(responseTextView);
        }
    }

    public void analyzeText(String text) {
        StringReader reader = new StringReader(text);
        TextLexer lexer = new TextLexer(reader);
        TextParser parser = new TextParser(lexer);

        try {
            parser.parse();
            if (lexer.getErrors().isEmpty() && parser.getSyntaxErrors().isEmpty()) {

                execute(parser.getElements());
            } else {
                if (responseTextView != null) {
                    responseTextView.setText("");
                    for (String lexerError : lexer.getErrors()) {
                        responseTextView.append(lexerError + "\n");
                    }
                    for (String syntaxError : parser.getSyntaxErrors()) {
                        responseTextView.append(syntaxError + "\n");
                    }
                }
            }
        } catch (Exception e) {
            if (responseTextView != null) {
                responseTextView.setText("Error inesperado: " + e.getMessage());
            }
        }
    }
}
