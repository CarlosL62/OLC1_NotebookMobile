package com.example.notebookmobile.text_analysis.elements;

import android.widget.TextView;

public abstract class Element {
    protected String text;

    public abstract void execute(TextView response);
}
