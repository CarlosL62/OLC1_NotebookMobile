package com.example.notebookmobile

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import com.example.notebookmobile.code_analysis.Analyzer

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val textInputButton = findViewById<Button>(R.id.textInputButton)
        val codeInputButton = findViewById<Button>(R.id.codeInputButton)
        val container = findViewById<LinearLayout>(R.id.container)

        textInputButton.setOnClickListener {
            val textItem = layoutInflater.inflate(R.layout.text_item, null)
            val editText = textItem.findViewById<EditText>(R.id.editTextTextMultiLine)
            val executeButton = textItem.findViewById<Button>(R.id.executeButton)
            val responseTextView = textItem.findViewById<TextView>(R.id.textResponse)

            container.addView(textItem)

            executeButton.setOnClickListener {
                val response = editText.text.toString()
                responseTextView.text = response
            }
        }

        codeInputButton.setOnClickListener {
            val codeItem = layoutInflater.inflate(R.layout.code_item, null)
            val editText = codeItem.findViewById<EditText>(R.id.editTextTextMultiLine)
            val executeButton = codeItem.findViewById<Button>(R.id.executeButton)
            val responseTextView = codeItem.findViewById<TextView>(R.id.codeResponse)

            container.addView(codeItem)


            executeButton.setOnClickListener {
                val response = editText.text.toString()
                val analyzer = Analyzer(responseTextView)
                analyzer.executeCode(response)
            }
        }
    }
}