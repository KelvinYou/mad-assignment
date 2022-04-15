package com.example.assignment

import android.app.ProgressDialog
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.TextUtils
import android.text.style.ForegroundColorSpan
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import com.example.assignment.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    private lateinit var actionBar: ActionBar

    private lateinit var progessDialog: ProgressDialog

    private lateinit var firebaseAuth: FirebaseAuth

    private var email=""
    private var password=""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        (Color.parseColor("#F69300"))
        val text1="Solution 4U"
        val spanString=SpannableString(text1)
        val white=ForegroundColorSpan(Color.WHITE)
        val orange=ForegroundColorSpan(Color.parseColor("#F69300"))

        spanString.setSpan(white,0,8, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        spanString.setSpan(orange,9,11, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

        binding.tvLogoText.text=spanString


        //config action bar
        actionBar=supportActionBar!!
        actionBar.title="Login"

        //config progress
        progessDialog =ProgressDialog(this)
        progessDialog.setTitle("Please Wait")
        progessDialog.setMessage("Logging In...")
        progessDialog.setCanceledOnTouchOutside(false)

        //setup firebase
        firebaseAuth= FirebaseAuth.getInstance()
        checkUser()

        //handle click
        binding.noAccountTv.setOnClickListener{
            startActivity(Intent(this,SignUpActivity::class.java))
        }


        //begin login
        binding.loginBtn.setOnClickListener{
            validateData()
        }


    }

    private fun validateData() {
        email=binding.emailEt.text.toString().trim()
        password=binding.PasswordEt.text.toString().trim()

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.emailEt.setError("Invalid Email Format")

        }else if(TextUtils.isEmpty(password)){
            binding.PasswordEt.error="Please Enter Password"
        }else{
            firebaseLogin()
        }
    }

    private fun firebaseLogin() {
        progessDialog.show()
        firebaseAuth.signInWithEmailAndPassword(email,password)
            .addOnSuccessListener {
                //login success
                progessDialog.dismiss()
                val firebaseUser=firebaseAuth.currentUser
                val email=firebaseUser!!.email
                Toast.makeText(this,"Logged In as $email",Toast.LENGTH_SHORT).show()
                startActivity(Intent(this,MainActivity::class.java))
                finish()
            }
            .addOnFailureListener{ e->
                progessDialog.dismiss()
                Toast.makeText(this,"Login Failed due to ${e.message}",Toast.LENGTH_SHORT).show()

            }
    }

    private fun checkUser()
    {
        val firebaseUser=firebaseAuth.currentUser
        if(firebaseUser!=null){
            startActivity(Intent(this,MainActivity::class.java))
            finish()
        }
    }


}


