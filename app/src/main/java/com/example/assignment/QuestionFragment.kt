package com.example.assignment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class QuestionFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_question, container, false).apply {

        var firebaseAuth = FirebaseAuth.getInstance()

        var realtimeDB = FirebaseDatabase.getInstance().getReference("Questions")

        val quesInputTitle = findViewById<EditText>(R.id.quesInputTitle)
        val quesInputBody = findViewById<EditText>(R.id.quesInputBody)
        val quesInputTags = findViewById<EditText>(R.id.quesInputTags)
        val btnUploadImg: Button = findViewById(R.id.btnUploadImg)
        val btnQuesPost: Button = findViewById(R.id.btnQuesPost)
        val btnQuesReview: Button = findViewById(R.id.btnQuesReview)

        btnQuesPost.setOnClickListener {
            var title = quesInputTitle.text.toString()
            var body = quesInputBody.text.toString()
            var tags = quesInputTags.text.toString()
            var userID = firebaseAuth.uid!!.toString()

            if(title.isEmpty()){
                quesInputTitle.error = "Please enter title"

            }else if(body.isEmpty()) {
                    quesInputBody.error = "Please enter body"
                }else if (tags.isEmpty()) {
                    quesInputTags.error = "Please enter tags"
                }else {
                    realtimeDB.child(title.toString()).setValue(Ask(title, body, tags,userID.toString())).addOnSuccessListener {
                        Toast.makeText(activity,"Your question is posted.", Toast.LENGTH_LONG).show()
                    }.addOnFailureListener{
                        Toast.makeText(activity,"Your question is failed to post.", Toast.LENGTH_LONG).show()
                    }
                }
        }

        btnQuesReview.setOnClickListener {
            val intent = Intent (activity, QuestionsList::class.java)
            activity?.startActivity(intent)
            val userid = intent.getStringExtra("userID").toString() ?: "null"
        }

        btnUploadImg.setOnClickListener {
            val intent = Intent (activity, QuestionImage::class.java)
            activity?.startActivity(intent)
        }

    }
}