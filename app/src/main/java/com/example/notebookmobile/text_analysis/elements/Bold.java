package com.example.notebookmobile.text_analysis.elements;

import android.graphics.Typeface;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.widget.TextView;

public class Bold extends Element {
    public Bold(String text) {
        this.text = text;
    }

    @Override
    public void execute(TextView response) {
        SpannableString builder = new SpannableString(text);
        builder.setSpan(new StyleSpan(Typeface.BOLD), 0, text.length(), builder.SPAN_EXCLUSIVE_EXCLUSIVE);
        response.append(builder);
        response.append("\n");
    }
}
