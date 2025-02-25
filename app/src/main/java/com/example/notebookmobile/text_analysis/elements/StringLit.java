package com.example.notebookmobile.text_analysis.elements;

import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.widget.TextView;

public class StringLit extends Element {
    public StringLit(String text) {
        this.text = text;
    }

    @Override
    public void execute(TextView response) {
        SpannableStringBuilder builder = new SpannableStringBuilder();
        builder.append(text);
        response.append(builder);
        response.append("\n");
    }
}
