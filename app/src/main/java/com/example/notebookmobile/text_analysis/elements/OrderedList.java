package com.example.notebookmobile.text_analysis.elements;

import android.widget.TextView;

public class OrderedList extends Element {
    private String content;
    static int count = 1;

    public OrderedList(String content) {
        this.content = content;
        this.count = 1;
    }

    @Override
    public void execute(TextView response) {
        final int index = count;
        response.append(index + ". " + content + "\n");
        count++;
    }
}
