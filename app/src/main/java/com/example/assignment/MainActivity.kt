package com.example.assignment

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
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

//        val homeFragment = HomeFragment()
//        val questionFragment = QuestionFragment()
//        val answerFragment = AnswerFragment()
//        val profileFragment = ProfileFragment()
//
//        val navigation = intent.getIntExtra("navigation",0)
//
//        if(navigation == 0){
//            makeCurrentFragment(homeFragment)
//        }else {
//            when(navigation){
//                1 -> {
//                    binding.bottomNavigation.selectedItemId = R.id.ic_home
//                    makeCurrentFragment(homeFragment)
//                }
//                2 -> {
//                    binding.bottomNavigation.selectedItemId = R.id.ic_question
//                    makeCurrentFragment(questionFragment)
//                }
//                2 -> {
//                    binding.bottomNavigation.selectedItemId = R.id.ic_answer
//                    makeCurrentFragment(answerFragment)
//                }
//                4 -> {
//                    binding.bottomNavigation.selectedItemId = R.id.ic_profile
//                    makeCurrentFragment(profileFragment)
//                }
//            }
//        }
//
//        binding.bottomNavigation.setOnItemSelectedListener {
//            when(it.itemId){
//                R.id.ic_home -> makeCurrentFragment(homeFragment)
//                R.id.ic_question -> makeCurrentFragment(questionFragment)
//                R.id.ic_answer -> makeCurrentFragment(answerFragment)
//                R.id.ic_profile -> makeCurrentFragment(profileFragment)
//            }
//            true
//        }
    }

//    private fun makeCurrentFragment(fragment: Fragment) {
//        supportFragmentManager.beginTransaction().apply {
//            replace(R.id.fragmentContainerView, fragment)
//            commit()
//        }
//    }
}