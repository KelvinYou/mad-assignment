package com.example.assignment

import android.app.ProgressDialog
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.TextUtils
import android.text.style.ForegroundColorSpan
import android.util.Patterns
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import com.example.assignment.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.database.DatabaseReference as DatabaseReference
import com.google.firebase.firestore.DocumentReference as DocumentReference1

class SignUpActivity : AppCompatActivity() {

    private lateinit var binding:ActivitySignUpBinding

    private lateinit var actionBar:ActionBar

    private lateinit var progressDialog:ProgressDialog

    private lateinit var firebaseAuth: FirebaseAuth

    private lateinit var database: DatabaseReference

    private var email=""
    private var password=""
    private var name=""
    private var phone=""
    private var conpass=""







    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        (Color.parseColor("#F69300"))
        val text1="Solution 4U"
        val spanString= SpannableString(text1)
        val white= ForegroundColorSpan(Color.WHITE)
        val orange= ForegroundColorSpan(Color.parseColor("#F69300"))

        spanString.setSpan(white,0,8, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        spanString.setSpan(orange,9,11, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

        binding.tvsignupquote.text=spanString

        actionBar=supportActionBar!!
        actionBar.title="Sign Up"
        actionBar.setDisplayHomeAsUpEnabled(true)
        actionBar.setDisplayShowHomeEnabled(true)


        progressDialog= ProgressDialog(this)
        progressDialog.setTitle("Please Wait")
        progressDialog.setMessage("Creating Account")
        progressDialog.setCanceledOnTouchOutside(false)

        firebaseAuth=FirebaseAuth.getInstance()
        binding.SignupBtn.setOnClickListener {
            validateData()
        }


        binding.backLogin.setOnClickListener{
            startActivity(Intent(this,LoginActivity::class.java))
        }
    }

    private fun validateData() {

        name=binding.nameEt.text.toString().trim()
        phone=binding.phoneEt.text.toString().trim()
        conpass=binding.conPasswordEt.text.toString().trim()
        email=binding.emailEt.text.toString().trim()
        password=binding.PasswordEt.text.toString().trim()
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            binding.emailEt.error="Invalid Email Format"
        }
        else if(TextUtils.isEmpty(password)){
            binding.PasswordEt.error="Please Enter Password"
        }else if(password.length<6){
            binding.PasswordEt.error="Password must be atleast 6 Character Long"

        }else if(password!=conpass){
            binding.PasswordEt.error="Please make sure password is the same"

        }else if(TextUtils.isEmpty(name)){
            binding.nameEt.error="Name cannot be empty"

        } else if(TextUtils.isEmpty(phone)){
            binding.phoneEt.error="Phone number cannot be empty"

        } else {

            firebaseSignUp()
        }


    }

    private fun firebaseSignUp() {


        progressDialog.show()
        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                database= FirebaseDatabase.getInstance().getReference("user")
                val User=user(name,email,phone)
                database.child(name).setValue(User).addOnSuccessListener {  }

                Toast.makeText(this,"Account Created with email $email",Toast.LENGTH_SHORT).show()



                startActivity(Intent(this,ProfileActivity::class.java))
                finish()

            }
            .addOnFailureListener {e->
                progressDialog.dismiss()
                Toast.makeText(this,"SignUp Failed due to ${e.message} ",Toast.LENGTH_SHORT).show()
            }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

}