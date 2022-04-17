package com.example.assignment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.assignment.databinding.ActivityAnswerBinding
import com.example.assignment.databinding.FragmentCommentBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_answer.*
import kotlinx.android.synthetic.main.activity_main_qn.*
import java.text.SimpleDateFormat
import java.util.*

class AnswerActivity : AppCompatActivity() {
    private lateinit var database : DatabaseReference
    private lateinit var commentArrayList: ArrayList<Comment>
    private lateinit var commentRecycleView: RecyclerView
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var binding: ActivityAnswerBinding
    //private var layoutManager: RecyclerView.LayoutManager? = null
    //private var adapter: RecyclerView.Adapter<CommentListAdapter.ViewHolder>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityAnswerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //layoutManager = LinearLayoutManager(this)
        //commentDisplay.layoutManager = layoutManager
        //adapter = CommentListAdapter()
        //commentDisplay.adapter = adapter


        //commentRecycleView = binding.commentDisplay
        //commentRecycleView.layoutManager = LinearLayoutManager(this)
        //commentRecycleView.setHasFixedSize(true)
        //commentArrayList = arrayListOf<Comment>()
        //getCommentData()

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

        //val answerQuestionTitle = findViewById<EditText>(R.id.quesInputTitle)
        val answerQuestionTitle = "upload title"
        val answerComment = binding.tfComment
        val btnSendComment: ImageButton = binding.btnSendComment

        btnSendComment.setOnClickListener {
            val date = Calendar.getInstance().time
            val formatter = SimpleDateFormat.getDateTimeInstance() //or use getDateInstance()
            val formatedDate = formatter.format(date)

            //var title = answerQuestionTitle.text.toString()
            var ansQuestionTitle = answerQuestionTitle
            var ansComment = answerComment.text.toString()
            var ansDate: String = formatedDate

            realtimeDB.child(username).setValue(Comment(ansQuestionTitle,ansComment, ansDate, username))
        }
    }

    private fun getCommentData() {
        database = FirebaseDatabase.getInstance().getReference("Answer")

        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    for(commentSnapshot in snapshot.children){
                        val comment = commentSnapshot.getValue(Comment::class.java)
                        commentArrayList.add(comment!!)
                    }
                    var adapter = CommentListAdapter(commentArrayList)
                    commentRecycleView.adapter = adapter
                    adapter.setOnItemClickListener(object : CommentListAdapter.onItemClickedListener{
                        override fun onItemClick(position: Int) {
                            val intent = Intent(this@AnswerActivity, AnswerQuestions::class.java)
                            this@AnswerActivity.startActivity(intent)
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