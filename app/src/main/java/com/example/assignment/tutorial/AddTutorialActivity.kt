package com.example.assignment.tutorial

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.helper.widget.MotionEffect.TAG
import androidx.databinding.DataBindingUtil
import com.example.assignment.R
import com.example.assignment.data.Tutorial
import com.example.assignment.databinding.ActivityAddTutorialBinding
import com.example.assignment.models.TutorialViewModel
import com.example.assignment.models.cropToBlob
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.Blob
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.*

class AddTutorialActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddTutorialBinding
    val tr = TutorialViewModel()

    private val launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if (it.resultCode == RESULT_OK) {
            binding.imgAddTutorialImage.setImageURI(it.data?.data)
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_tutorial)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_tutorial)

        binding.btnPostTutorial.setOnClickListener{ post() }
        binding.addActPicBtn.setOnClickListener{chooseImage()}

        val actionbar = supportActionBar
        actionbar!!.title = "Add Tutorial"
        actionbar.setDisplayHomeAsUpEnabled(true)
        actionbar.setDisplayHomeAsUpEnabled(true)

        reset()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun post() {
        val tutorialTitle  = binding.edtTutorialTitle.text.toString().trim()
        val tutorialDetail = binding.edtTutorialDetail.text.toString()
        val currentDate = Date()

        if(tutorialTitle == ""){
            binding.edtTutorialTitle.error = "Please enter a title"
            binding.edtTutorialTitle.requestFocus()
            return
        }

        else if (tutorialDetail == ""){
            binding.edtTutorialDetail.error = "Please input content"
            binding.edtTutorialDetail.requestFocus()
            return
        }

        else if (wordCountCheck(tutorialTitle)) {
            binding.edtTutorialTitle.error = "Your title is more than 15 words"
            binding.edtTutorialTitle.requestFocus()
            return
        }

        val tutorial = Tutorial (
            title = tutorialTitle,
            content = tutorialDetail,
            status = "Posted",
            createdDate = currentDate,
            modifiedDate = currentDate,
            ownerID = Firebase.auth.currentUser?.email ?: "",
            Image =
            try {
                binding.imgAddTutorialImage.cropToBlob(200,200)
            }catch (e : Exception){
                Blob.fromBytes(ByteArray(0))
            }

        )

        tr.insert(tutorial)

        finish()

        reset()

    }

    private fun wordCountCheck(articleTitle: String): Boolean {
        val words = articleTitle.split("\\s+".toRegex())

        return words.size > 15
    }

    private fun chooseImage() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        launcher.launch(intent)
    }

    private fun reset() {
        with(binding){
            edtTutorialTitle.text.clear()
            edtTutorialDetail.text.clear()
            imgAddTutorialImage.setImageBitmap(null)

            edtTutorialTitle.requestFocus()

        }
    }
}