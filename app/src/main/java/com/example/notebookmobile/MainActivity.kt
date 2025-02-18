package com.example.notebookmobile

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.notebookmobile.ui.theme.NotebookMobileTheme

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
            container.addView(textItem)

            executeButton.setOnClickListener {
                val text = editText.text.toString()
                Log.d("MainActivity", "Texto: $text")
            }
        }

        codeInputButton.setOnClickListener {
            val codeItem = layoutInflater.inflate(R.layout.code_item, null)
            val editText = codeItem.findViewById<EditText>(R.id.editTextTextMultiLine)
            val executeButton = codeItem.findViewById<Button>(R.id.executeButton)
            container.addView(codeItem)

            executeButton.setOnClickListener {
                val code = editText.text.toString()
                Log.d("MainActivity", "Código: $code")
            }
        }
    }
}