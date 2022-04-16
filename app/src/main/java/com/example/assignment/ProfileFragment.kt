package com.example.assignment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.ActionBar
import com.example.assignment.databinding.ActivityProfileBinding
import com.example.assignment.databinding.FragmentProfileBinding
import com.google.firebase.auth.FirebaseAuth
import android.content.Intent as Intent


class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? =null
    private val binding get()=_binding!!



    private lateinit var actionBar: ActionBar

    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
        
    ): View? {
     
        _binding= FragmentProfileBinding.inflate(inflater,container,false)
        

        firebaseAuth= FirebaseAuth.getInstance()
        checkUser()

        binding.logoutBtn.setOnClickListener {
            firebaseAuth.signOut()
            checkUser()
        }


        binding.cancelBtn.setOnClickListener {

        }

        binding.saveBtn.setOnClickListener {

        }
        return binding.root
        
    }

    private fun checkUser() {
        val firebaseUser=firebaseAuth.currentUser
        if(firebaseUser!=null){
            val email=firebaseUser.email


        }else{


        }
    }
       

         }
