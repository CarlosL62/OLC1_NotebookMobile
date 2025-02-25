package com.example.notebookmobile.text_analysis.elements;

import android.widget.TextView;

public class OrderedList extends Element {
    private String content;

    public OrderedList(String content) {
        this.content = content;
    }

    @Override
    public void execute(TextView response) {
        String[] items = content.split("\n");
        StringBuilder formattedText = new StringBuilder();

        int index = 1;
        for (String item : items) {
            formattedText.append(index).append(". ").append(item).append("\n");
            index++;
        }

        response.append(formattedText.toString());
    }
}
