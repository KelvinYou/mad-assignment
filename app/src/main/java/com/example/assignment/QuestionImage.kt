package com.example.assignment

import android.app.ProgressDialog
import android.content.Intent
import android.icu.text.SimpleDateFormat
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.assignment.databinding.ActivityQuestionImageBinding
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.FirebaseDatabase.getInstance
import com.google.firebase.storage.FirebaseStorage
import java.net.URI
import java.util.*
import kotlin.collections.HashMap

class QuestionImage : AppCompatActivity() {

    lateinit var binding : ActivityQuestionImageBinding
    lateinit var ImageUri : Uri

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuestionImageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSelectImage.setOnClickListener {
            selectImage()
        }

        binding.btnUploadImage.setOnClickListener {
            uploadImage()
        }
    }

    private fun uploadImage() {
        val progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Uploading file ...")
        progressDialog.setCancelable(false)
        progressDialog.show()

        val formatter = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.getDefault())
        } else {
            TODO("VERSION.SDK_INT < N")
        }
        val now = Date()
        val fileName = formatter.format(now)
        val storageReference = FirebaseStorage.getInstance().getReference("QuestionImages/$fileName")


        storageReference.putFile(ImageUri).addOnSuccessListener (OnSuccessListener {

            storageReference.getDownloadUrl().addOnSuccessListener (OnSuccessListener<Uri> { uri ->
                val realtimeReference = FirebaseDatabase.getInstance()
                    .getReference("Questions").child("QuestionImages")
                val hashMap:HashMap<String, String> = HashMap()
                hashMap.put("imageUrl", uri.toString())
                realtimeReference.setValue(hashMap)
                Toast.makeText(this, "Successfully inserted into realtime database", Toast.LENGTH_SHORT).show()
            })

            binding.imageView.setImageURI(null)
            Toast.makeText(this@QuestionImage, "Successfully uploaded", Toast.LENGTH_SHORT).show()
            if(progressDialog.isShowing) progressDialog.dismiss()

        }).addOnFailureListener {
            if(progressDialog.isShowing) progressDialog.dismiss()
            Toast.makeText(this@QuestionImage, "Fail to upload", Toast.LENGTH_SHORT).show()
        }
    }

    private fun selectImage() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT

        startActivityForResult(intent, 100)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == 100 && resultCode == RESULT_OK) {
            ImageUri = data?.data!!
            binding.imageView.setImageURI(ImageUri)
            val imageView = intent.getStringExtra("imageView").toString() ?: "null"
        }
    }
}