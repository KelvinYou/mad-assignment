package com.example.assignment

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainQnA : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_qn)

        val btnAsk: Button = findViewById(R.id.btnAsk)
        btnAsk.setOnClickListener(){
            val intentA: Intent = Intent(this, AskQuestion::class.java)
            startActivity(intentA)
        }
    }
}