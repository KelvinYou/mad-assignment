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
import android.content.Intent
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? =null
    private val binding get()=_binding!!



    private lateinit var actionBar: ActionBar

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var database:DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
        
    ): View? {
     
        _binding= FragmentProfileBinding.inflate(inflater,container,false)
        

        firebaseAuth= FirebaseAuth.getInstance()
        checkUser()
        val name:String=binding.nameEt.text.toString()
        readData(name)

        binding.logoutBtn.setOnClickListener {
            firebaseAuth.signOut()
            checkUser()
            val intent = Intent (getActivity(), LoginActivity::class.java)
            getActivity()?.startActivity(intent)

        }


        binding.cancelBtn.setOnClickListener {

        }

        binding.saveBtn.setOnClickListener {

        }

        binding.changePassword.setOnClickListener {
            val intent = Intent (getActivity(), ChangePassword::class.java)
            getActivity()?.startActivity(intent)
        }

        return binding.root
        
    }

    private fun checkUser() {
        val firebaseUser=firebaseAuth.currentUser
        if(firebaseUser!=null){
            val email=firebaseUser.email


        }else{
            val intent = Intent (getActivity(), LoginActivity::class.java)
            getActivity()?.startActivity(intent)


        }
    }

    private fun readData(name:String){
        database=FirebaseDatabase.getInstance().getReference("user")
        database.child(name).get().addOnSuccessListener {
            if(it.exists()){
                val name=it.child("name").value
                val email=it.child("email").value
                val phone=it.child("phone").value


            }

        }
    }
       

         }
