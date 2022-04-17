package com.example.assignment

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.assignment.databinding.ActivityAnswerBinding
import com.example.assignment.databinding.ActivityUserOwnCommentBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import java.text.SimpleDateFormat
import java.util.*

class UserOwnComment : AppCompatActivity() {
    private lateinit var database : DatabaseReference
    private lateinit var commentArrayList: ArrayList<Comment>
    private lateinit var commentRecycleView: RecyclerView
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var binding: ActivityUserOwnCommentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserOwnCommentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        commentRecycleView = binding.titleCommentDisplay
        commentRecycleView.layoutManager = LinearLayoutManager(this)
        commentRecycleView.setHasFixedSize(true)
        commentArrayList = arrayListOf<Comment>()

        firebaseAuth= FirebaseAuth.getInstance()
        val id = firebaseAuth.uid!!
        var username: String = "hello"
        val userDB = FirebaseDatabase.getInstance().getReference("user")
        userDB.child(id).get().addOnSuccessListener {
            username = it.child("name").value as String
        }.addOnFailureListener{
            username = "fail"
        }

        binding.tvInstru.text = username
        getCommentData(username)

    }

    private fun getCommentData(username: String) {
        database = FirebaseDatabase.getInstance().getReference("Answer")

        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    for(commentSnapshot in snapshot.children){
                        val comment = commentSnapshot.getValue(Comment::class.java)
                        if (comment != null) {
                            if (comment.ansIdName == username) {
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