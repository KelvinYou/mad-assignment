package com.example.assignment.tutorial

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.assignment.MainActivity
import com.example.assignment.R
import com.example.assignment.data.Tutorial
import com.example.assignment.data.TutorialLike
import com.example.assignment.data.UserProfile
import com.example.assignment.databinding.ActivityTutorialDetailBinding
import com.example.assignment.models.*
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.text.SimpleDateFormat
import java.util.*

class TutorialDetailActivity : AppCompatActivity() {

    lateinit var binding : ActivityTutorialDetailBinding
    val ar : TutorialViewModel by viewModels()
    val lk : TutorialLikeViewModel by viewModels()
    val cm : TutorialCommentViewModel by viewModels()
    val ur : UserViewModel by viewModels()
    lateinit var a  : Tutorial
    lateinit var owner : UserProfile
    private val col = Firebase.firestore.collection("TutorialLike")
    lateinit var from : String
//    lateinit var callbackManager : CallbackManager
//    lateinit var shareDialog : ShareDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tutorial_detail)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_tutorial_detail)
//
        val id = intent.getStringExtra("id") ?: ""
        val isAdmin = intent.getBooleanExtra("isAdmin",false)
        from = intent.getStringExtra("fromActivity") ?: ""

//        callbackManager = CallbackManager.Factory.create()
//        shareDialog = ShareDialog(this)

        binding.btnLike.setOnClickListener { like(id) }
        binding.btnComment.setOnClickListener { comment(id) }
//        binding.btnDeleteTutorialAdmin.setOnClickListener { delete() }
//        binding.btnCancelDeleteAdmin.setOnClickListener { cancelDelete() }
//        binding.btnShareTutorial.setOnClickListener { shareTutorial() }
//
//        val actionbar = supportActionBar
//        actionbar!!.title = "Tutorial Detail"
//        actionbar.setDisplayHomeAsUpEnabled(true)
//        actionbar.setDisplayHomeAsUpEnabled(true)

        reload(id, isAdmin)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

//    private fun shareTutorial() {
//
//        val link = generateLink()
//
//        val linkContent = ShareLinkContent.Builder()
//            .setQuote(a.title)
//            .setContentUrl(link)
//            .build()
//
//        if (ShareDialog.canShow(ShareLinkContent::class.java)) {
//            shareDialog.show(linkContent)
//        }
//    }
//
//    private fun generateLink(): Uri {
//        val dynamicLink = Firebase.dynamicLinks.dynamicLink {
//            link = Uri.parse("https://www.agmo.com/?type=Article&id=${a.id}")
//            domainUriPrefix = "https://agmo.page.link"
//            // Open links with this app on Android
//            androidParameters { }
//
//            socialMetaTagParameters {
//                title = a.title
//            }
//        }
//
//        return  dynamicLink.uri
////        Log.e("LLL", "$dynamicLinkUri")
//    }


    private fun comment(id: String) {
        val intent = Intent(this, TutorialCommentActivity::class.java)
            .putExtra("id", id)
        startActivity(intent)
    }

    private fun like(id: String) {

        val currentDate = Date()
        val like = TutorialLike (
            date = currentDate,
            tutorialID = id,
            userID = Firebase.auth.currentUser?.email ?: ""
        )

        if(lk.insert(like)){
            val title = "Someone Like Your Article"
            val message = "${Firebase.auth.currentUser?.email} like your article (${a.title})"
            val type = "Like"
            val id = a.id

//            PushNotification(
//                NotificationData(
//                    title = title,
//                    message = message,
//                    type = type,
//                    id = id,
//                ),
//                owner.token
//            ).also{
//                sendNotification(it,"Tutorial_Detail")
//            }
        }
        binding.btnLike.setImageResource(R.drawable.ic_like)
    }


//    private fun cancelDelete() {
//        a.status = "Posted"
//        a.modifiedDate = Date()
//        ar.set(a)
//        finish()
//    }

//    private fun delete() {
//        a.status = "Deleted"
//        a.modifiedDate = Date()
//        ar.set(a)
//        val title = "Your Article be Deleted"
//        val message = "Admin delete your article (${a.title})"
//        val type = "Delete Article"
//        val id = a.id

//        PushNotification(
//            NotificationData(
//                title = title,
//                message = message,
//                type = type,
//                id = id,
//            ),
//            owner.token
//        ).also{
//            sendNotification(it,"Article_Delete")
//        }
//        finish()
//    }

    private fun reload(id: String, isAdmin : Boolean) {
        ar.getAll().observe(this){

            //Count Total Like
            lk.getAll().observe(this){
                for (userLike in it){
                    if(userLike.tutorialID == id && userLike.userID == Firebase.auth.currentUser?.email ?: "" ){
                        binding.btnLike.setImageResource(R.drawable.ic_dislike)
                    }
                }
                binding.tvLike.text = lk.getCountUser(id).toString()
            }

            // Count Total Comment
            cm.getAll().observe(this){
                binding.tvTotalComment.text = cm.getCountUser(id).toString()
            }

            val tutorial = ar.get(id)
            if(tutorial != null) {
                a = tutorial
                with(binding){
                    tvUserEmail.text = tutorial.ownerID
                    tvDate.text = SimpleDateFormat("dd-MM-yyyy").format(tutorial.createdDate)
                    tvTime.text = SimpleDateFormat("hh:mm aa").format(tutorial.createdDate)
                    tutorialDetailTitle.text = tutorial.title
                    tutorialDetailContent.text = tutorial.content
                    tutorialDetailImage.setImageBitmap(tutorial.Image.toBitmap())

//                    ur.getAll().observe(this@TutorialDetailActivity){
//                        owner = ur.get(tutorial.ownerID)!!
//                    }

//                    if(isAdmin){
//
//                        btnComment.visibility = View.GONE
//                        btnLike.visibility = View.GONE
//                        btnShareTutorial.visibility = View.GONE
//
//                    }else{
//                        btnDeleteTutorialAdmin.visibility = View.GONE
//                        btnCancelDeleteAdmin.visibility = View.GONE
//                    }

                }
            }else{
                val intent = Intent(this@TutorialDetailActivity,MainActivity::class.java)
                startActivity(intent)
            }
        }

    }

    override fun onBackPressed(){
        if(from == "Main"){
            val setIntent = Intent(this, MainActivity::class.java)
            startActivity(setIntent)
        }else{
            finish()
        }
    }
}