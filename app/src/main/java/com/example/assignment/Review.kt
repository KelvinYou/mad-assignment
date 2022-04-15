package com.example.assignment

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class Review : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_review)

        val btnAskQuestionInReview: Button = findViewById(R.id.btnAskQuestionInReview)
        btnAskQuestionInReview.setOnClickListener(){
            val intentB: Intent = Intent(this, AskQuestion::class.java)
            startActivity(intentB)
        }
    }
}