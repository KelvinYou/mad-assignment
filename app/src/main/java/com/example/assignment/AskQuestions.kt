package com.example.assignment

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.google.firebase.database.FirebaseDatabase

class AskQuestions : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ask_questions)

        var database = FirebaseDatabase.getInstance().getReference("Questions")

        val editTextTitle = findViewById<EditText>(R.id.quesInputTitle)
        val editTextBody = findViewById<EditText>(R.id.quesInputBody)
        val editTextTags = findViewById<EditText>(R.id.quesInputTags)
        val buttonPost: Button = findViewById(R.id.btnQuesPost)

        buttonPost.setOnClickListener {
            var askTitle = editTextTitle.text.toString()
            var askBody = editTextBody.text.toString()
            var askTags = editTextTags.text.toString()

            //database.push().setValue(Ask(askTitle,askBody,askTags))
            database.child(askTitle.toString()).setValue(Ask(askTitle,askBody,askTags))
        }

        val btnQuesList: Button = findViewById(R.id.btnQuesReview)
        btnQuesList.setOnClickListener(){
            val intentA: Intent = Intent(this, QuestionsList::class.java)
            startActivity(intentA)
        }
    }
}