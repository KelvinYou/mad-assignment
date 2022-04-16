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
import com.google.firebase.database.*


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
        loadUserInfo()

        binding.logoutBtn.setOnClickListener {
            firebaseAuth.signOut()
            checkUser()

            val intent = Intent (getActivity(), LoginActivity::class.java)
            getActivity()?.startActivity(intent)

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

    private fun loadUserInfo() {
        val ref=FirebaseDatabase.getInstance().getReference("user")
        ref.child(firebaseAuth.uid!!)
            .addValueEventListener(object : ValueEventListener {

                override fun onDataChange(snapshot: DataSnapshot) {
                    val email= "${snapshot.child("email").value}"
                    val name="${snapshot.child("name").value}"
                    val phone="${snapshot.child("phone").value}"
                    val photo="${snapshot.child("photo").value}"
                    val uid="${snapshot.child("uid").value}"

                    binding.nameEt.text=name
                    binding.emailEt.text=email
                    binding.phoneEt.text=phone



                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })
    }



       

         }
