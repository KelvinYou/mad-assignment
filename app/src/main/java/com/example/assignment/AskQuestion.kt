package com.example.assignment

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class AskQuestion : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ask_question)

        val btnReview: Button = findViewById(R.id.btnReview)
        btnReview.setOnClickListener(){
            val intentB: Intent = Intent(this, Review::class.java)
            startActivity(intentB)
        }
    }
}