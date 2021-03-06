package com.example.assignment

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.assignment.databinding.ActivityAnswerBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import java.text.SimpleDateFormat
import java.util.*

class AnswerActivity : AppCompatActivity() {
    private lateinit var database : DatabaseReference
    private lateinit var commentArrayList: ArrayList<Comment>
    private lateinit var commentRecycleView: RecyclerView
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var binding: ActivityAnswerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityAnswerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val quesTitle = intent.getStringExtra("quesTitle").toString() ?: "null"
        binding.tvQuestion.text = quesTitle

        firebaseAuth = FirebaseAuth.getInstance()

        commentRecycleView = binding.commentDisplay
        commentRecycleView.layoutManager = LinearLayoutManager(this)
        commentRecycleView.setHasFixedSize(true)
        commentArrayList = arrayListOf<Comment>()
        getCommentData(quesTitle)

        firebaseAuth= FirebaseAuth.getInstance()
        val id = firebaseAuth.uid!!
        var username: String = "hello"
        val userDB = FirebaseDatabase.getInstance().getReference("user")
        userDB.child(id).get().addOnSuccessListener {
            username = it.child("name").value as String
        }.addOnFailureListener{
            username = "fail"
        }

        var realtimeDB = FirebaseDatabase.getInstance().getReference("Answer")

        val answerComment = binding.tfComment
        val btnSendComment: ImageButton = binding.btnSendComment

        btnSendComment.setOnClickListener {
            val date = Calendar.getInstance().time
            val formatter = SimpleDateFormat.getDateTimeInstance() //or use getDateInstance()
            val formatedDate = formatter.format(date)

            var ansQuestionTitle = quesTitle
            var ansComment = answerComment.text.toString()
            var ansDate: String = formatedDate

            realtimeDB.child(ansDate).setValue(Comment(ansQuestionTitle,ansComment, ansDate, username))
                .addOnSuccessListener {
                    Toast.makeText(this,"Your comment is posted.", Toast.LENGTH_LONG).show()
                }.addOnFailureListener{
                    Toast.makeText(this,"Your comment is failed to post.", Toast.LENGTH_LONG).show()
                }

            finish()
            overridePendingTransition(0, 0)
            startActivity(intent)
            overridePendingTransition(0, 0)
        }
    }

    private fun getCommentData(quesTitle: String) {
        database = FirebaseDatabase.getInstance().getReference("Answer")

        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                commentArrayList.clear()
                if(snapshot.exists()){
                    for(commentSnapshot in snapshot.children){
                        val comment = commentSnapshot.getValue(Comment::class.java)
                        if (comment != null) {
                            if (comment.ansQuestionTitle == quesTitle) {
                                commentArrayList.add(comment!!)
                            }
                        }
                    }
                    var adapter = CommentListAdapter(commentArrayList)
                    commentRecycleView.adapter = adapter
                    adapter.setOnItemClickListener(object : CommentListAdapter.onItemClickedListener{
                        override fun onItemClick(position: Int) {

                        }
                    })
                }
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }


}