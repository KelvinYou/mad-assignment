package com.example.assignment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import com.google.firebase.database.FirebaseDatabase

class QuestionFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? = inflater.inflate(R.layout.fragment_question, container, false).apply {

        var realtimeDB = FirebaseDatabase.getInstance().getReference("Questions")

        val quesInputTitle = findViewById<EditText>(R.id.quesInputTitle)
        val quesInputBody = findViewById<EditText>(R.id.quesInputBody)
        val quesInputTags = findViewById<EditText>(R.id.quesInputTags)
        val btnQuesPost: Button = findViewById(R.id.btnQuesPost)
        val btnQuesReview: Button = findViewById(R.id.btnQuesReview)

        btnQuesPost.setOnClickListener {
            var title = quesInputTitle.text.toString()
            var body = quesInputBody.text.toString()
            var tags = quesInputTags.text.toString()

            realtimeDB.child(title.toString()).setValue(Ask(title,body,tags))
        }

        btnQuesReview.setOnClickListener(){
            val intent = Intent (activity, QuestionsList::class.java)
            activity?.startActivity(intent)
        }
        //return inflater.inflate(R.layout.fragment_question, container, false)
    }

}