package com.example.assignment

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.assignment.databinding.ActivityChangePasswordBinding
import com.example.assignment.databinding.ActivityLoginBinding
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_change_password.*


class ChangePassword : AppCompatActivity() {

    private lateinit var binding: ActivityChangePasswordBinding

    private lateinit var auth : FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityChangePasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth= FirebaseAuth.getInstance()
        binding.Cancelbutton.setOnClickListener {
           onBackPressed()

        }
        binding.Changebutton.setOnClickListener {
            changepass()
        }



    }

    private fun changepass() {
        if(OriPasswordEt.text.isNotEmpty()&&NewPasswordEt.text.isNotEmpty()&&ConPasswordEt.text.isNotEmpty()){
            if(NewPasswordEt.text.toString().equals(ConPasswordEt.text.toString())){
                    val user= auth.currentUser
                if(user!=null){
                        val credential=EmailAuthProvider.getCredential(user.email!!,OriPasswordEt.text.toString())

                    user?.reauthenticate(credential)?.addOnCompleteListener{
                        if(it.isSuccessful){
                            Toast.makeText(this,"Old Password Correct",Toast.LENGTH_SHORT).show()
                            user?.updatePassword(NewPasswordEt.text.toString())?.addOnCompleteListener{task->
                                Toast.makeText(this,"Password Have Been Changed.",Toast.LENGTH_SHORT).show()
                                onBackPressed()
                            }
                        }else{
                            Toast.makeText(this,"ReAuthentication Failed",Toast.LENGTH_SHORT).show()
                        }
                    }
                }else  {
                    startActivity(Intent(this,LoginActivity::class.java))
                }
            }else{
                Toast.makeText(this,"Confirm Password does not match New Password",Toast.LENGTH_SHORT).show()
            }

        }else{
            Toast.makeText(this,"Please Fill all the fields.",Toast.LENGTH_SHORT).show()
        }
    }


}