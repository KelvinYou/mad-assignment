package com.example.assignment

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.util.Patterns.EMAIL_ADDRESS
import android.widget.Toast

import com.example.assignment.databinding.ActivityForgotPasswordBinding
import com.google.firebase.auth.FirebaseAuth
import java.util.regex.Pattern

class ForgotPassword : AppCompatActivity() {
    private lateinit var binding:ActivityForgotPasswordBinding

    private lateinit var firebaseAuth: FirebaseAuth

    private lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityForgotPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth= FirebaseAuth.getInstance()

        progressDialog= ProgressDialog(this)
        progressDialog.setTitle("Loading")
        progressDialog.setCanceledOnTouchOutside(false)

        binding.backbtn.setOnClickListener {
            onBackPressed()
        }

        binding.Reset.setOnClickListener {
            validateData()
        }

    }

    private var email=""
    private fun validateData() {
        email=binding.emailEt.text.toString().trim()
        if(email.isEmpty()){
            Toast.makeText(this,"Please Enter an Email to reset password.",Toast.LENGTH_SHORT).show()

        }else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            Toast.makeText(this,"Please Enter a Valid Email Address.",Toast.LENGTH_SHORT).show()

        }else{
            recoverpassword()
        }

    }

    private fun recoverpassword() {
        progressDialog.setMessage("Sending password reset instructions to $email")
        progressDialog.show()
        firebaseAuth.sendPasswordResetEmail(email)
            .addOnSuccessListener {
                progressDialog.dismiss()
                Toast.makeText(this,"Instruction Sent to \n$email",Toast.LENGTH_SHORT).show()

            }.addOnFailureListener {e->
                progressDialog.dismiss()
                Toast.makeText(this,"Failed to send due to ${e.message}",Toast.LENGTH_SHORT).show()


            }
    }


}