package com.example.notebookmobile.text_analysis.elements;

import android.widget.TextView;

public class UnorderedList extends Element {
    private String content;

    public UnorderedList(String content) {
        this.content = content;
    }

    @Override
    public void execute(TextView response) {
        String[] items = content.split("\n");
        StringBuilder formattedText = new StringBuilder();

        for (String item : items) {
            formattedText.append("• ").append(item).append("\n");
        }

        response.append(formattedText.toString());
    }
}
