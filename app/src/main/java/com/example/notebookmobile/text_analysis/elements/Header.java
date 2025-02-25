package com.example.notebookmobile.text_analysis.elements;

import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.RelativeSizeSpan;
import android.widget.TextView;

public class Header extends Element {

    private int level;

    public Header(int level, String text) {
        this.level = level;
        this.text = text;
    }

    @Override
    public void execute(TextView response) {
        SpannableString builder = new SpannableString(text);
        float size = 2.0f - (level * 0.2f); // H1 bigger, H6 smaller
        builder.setSpan(new RelativeSizeSpan(size), 0, text.length(), builder.SPAN_EXCLUSIVE_EXCLUSIVE);
        response.append(builder);
        response.append("\n");
    }
}
