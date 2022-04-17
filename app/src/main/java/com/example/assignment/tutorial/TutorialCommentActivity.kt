package com.example.assignment.tutorial

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.example.assignment.R
import com.example.assignment.data.Tutorial
import com.example.assignment.data.TutorialComment
import com.example.assignment.data.TutorialCommentAdapter
import com.example.assignment.databinding.ActivityTutorialCommentBinding
import com.example.assignment.models.TutorialCommentViewModel
import com.example.assignment.models.TutorialViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import org.w3c.dom.Comment
import java.util.*

class TutorialCommentActivity : AppCompatActivity() {
    lateinit var binding : ActivityTutorialCommentBinding
    val cm = TutorialCommentViewModel()
//    val us = UserViewModel()
    val ar : TutorialViewModel by viewModels()
//    lateinit var owner : UserProfile
    lateinit var a : Tutorial

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tutorial_comment)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_tutorial_comment)

        val id = intent.getStringExtra("id") ?: ""

        binding.btnSubmitComment.setOnClickListener{ post(id) }

        ar.getAll().observe(this){
            val tutorial = ar.get(id)
            if(tutorial != null) {
                a = tutorial
//                us.getAll().observe(this){
//                    val user =  us.get(a.ownerID)
//                    if(user!=null){
//                        owner = user
//                    }
//                }
            }
        }

        val adapter = TutorialCommentAdapter(){holder, tutorialComment ->
            val u = Firebase.auth.currentUser?.email ?: ""
            if (u != null) {
//                holder.tutorialUserImage.setImageBitmap(u.image.toBitmap())
            }
        }

        //Recycler View
        val recyclerView = binding.tutorialComment

        cm.getAll().observe(this) { tutorialComment ->
            var newList = tutorialComment.filter { c -> c.tutorialID == id}

            newList = newList.sortedBy { c -> c.date }

            adapter.submitList(newList)
            recyclerView.adapter = adapter
            recyclerView.setHasFixedSize(true)
        }

        val actionbar = supportActionBar
        actionbar!!.title = "Comment"
        actionbar.setDisplayHomeAsUpEnabled(true)
        actionbar.setDisplayHomeAsUpEnabled(true)

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun post(id: String) {
        val commentContent = binding.addComment.text.toString()
        val currentDate = Date()

        if(commentContent == ""){
            binding.addComment.error = "The comment cannot be blank!"
            binding.addComment.requestFocus()
            return
        }

        val comment = TutorialComment(
            content = commentContent,
            date = currentDate,
            userID = Firebase.auth.currentUser?.email ?: "",
            tutorialID = id

        )

        cm.insert(comment)
        binding.addComment.text.clear()
////        KToasty.success(this,"Post comment successfully.", Toast.LENGTH_LONG).show()
        closeKeyBoard()
//        val recyclerView = binding.tutorialComment
//        cm.getAll().observe(this) { tutorialComment ->
//            var newList = tutorialComment.filter { c -> c.tutorialID == id}
//
//            newList = newList.sortedBy { c -> c.date }
//            recyclerView.smoothScrollToPosition(newList.size - 1);
//        }
//        PushNotification(
//            NotificationData(
//                title = "Someone Comment on Your Tutorial",
//                message = "${Firebase.auth.currentUser?.email} comment your tutorial (${a.title})",
//                type = "Comment",
//                id = id,
//            ),
//            owner.token
//        ).also{
//            sendNotification(it,"Comment")
//        }

    }

    private fun closeKeyBoard() {
        val view = this.currentFocus
        if (view != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }
}