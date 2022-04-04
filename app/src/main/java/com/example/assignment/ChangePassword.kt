package com.example.assignment

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.assignment.databinding.ActivityChangePasswordBinding
import com.example.assignment.databinding.ActivityLoginBinding



class ChangePassword : AppCompatActivity() {

    private lateinit var binding: ActivityChangePasswordBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityChangePasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.Cancelbutton.setOnClickListener {
            startActivity(Intent(this,ProfileActivity::class.java))
        }
        binding.Changebutton.setOnClickListener {

        }



    }


}