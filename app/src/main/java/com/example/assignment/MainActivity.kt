package com.example.assignment

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.assignment.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
//    lateinit var binding: ActivityMainBinding
//    lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)
//        auth = Firebase.auth
//
    }

//    private fun makeCurrentFragment(fragment: Fragment) {
//        supportFragmentManager.beginTransaction().apply {
//            replace(R.id.fragmentContainerView, fragment)
//            commit()
//        }
//    }
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
//
//        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)
//        val navController = findNavController(R.id.fragmentContainerView)
//
//        val appBarConfiguration = AppBarConfiguration(setOf(R.id.homeFragment, R.id.questionFragment, R.id.answerFragment, R.id.profileFragment))
//
//        setupActionBarWithNavController(navController, appBarConfiguration)
//        bottomNavigationView.setupWithNavController(navController)
//
//
//    }
}