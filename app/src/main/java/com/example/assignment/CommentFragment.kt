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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.assignment.databinding.FragmentCommentBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_main_qn.*
import java.text.SimpleDateFormat
import java.util.*

class CommentFragment : Fragment() {
    //private var layoutManager: RecyclerView.LayoutManager? = null
   // private var adapter: RecyclerView.Adapter<CommentListAdapter.ViewHolder>? = null
    private lateinit var firebaseAuth: FirebaseAuth
    private var _binding: FragmentCommentBinding? =null
    private val binding get()=_binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
       // layoutManager = LinearLayoutManager(activity)

       // commentRecyclerView.LayoutManager = layoutManager

        //adapter = CommentListAdapter()
        //recyclerView.adapter = adapter

        _binding= FragmentCommentBinding.inflate(inflater,container,false)

        firebaseAuth= FirebaseAuth.getInstance()
        val id = firebaseAuth.uid!!
        var username: String = "hello"
        var userDB = FirebaseDatabase.getInstance().getReference("user")
        userDB.child(id).get().addOnSuccessListener {
            username = it.child("name").value as String
        }.addOnFailureListener{
            username = "fail"
        }

        //var ansIdName = readUsername(id)

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

            realtimeDB.child(ansQuestionTitle).child(username).setValue(Comment(ansQuestionTitle,ansComment, ansDate, username))
        }

        return binding.root
    }

    private fun readUsername(id: String): String {
        var username: String = "fail?"
        var userDB = FirebaseDatabase.getInstance().getReference("user")
        userDB.child(id).get().
        addOnSuccessListener {
            username = it.child("name").value as String
            Log.i("firebase", "Got value ${it.value}")
        }.addOnFailureListener{
            Log.e("firebase", "Error getting data", it)
        }
        return username
    }

}