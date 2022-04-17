package com.example.assignment

import android.app.Activity
import android.app.ProgressDialog
import android.content.ContentValues
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.Menu
import android.widget.PopupMenu
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.ActionBar
import com.bumptech.glide.Glide
import com.example.assignment.databinding.ActivityProfileBinding
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage

class ProfileActivity : AppCompatActivity() {

    private lateinit var binding:ActivityProfileBinding

    private lateinit var actionBar: ActionBar

    private lateinit var firebaseAuth: FirebaseAuth


    private lateinit var progressdialog:ProgressDialog

    //image uri
    private var imageUri: Uri?=null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        progressdialog= ProgressDialog(this  )
        progressdialog.setTitle("Please Wait")
        progressdialog.setCanceledOnTouchOutside(false)

        actionBar=supportActionBar!!
        actionBar.title="Profile"

        firebaseAuth= FirebaseAuth.getInstance()
        checkUser()
        loadUserInfo()


        binding.cancelBtn.setOnClickListener {
            onBackPressed()
        }

        binding.saveBtn.setOnClickListener {
            validateData()
            onBackPressed()
        }

        binding.profilepic.setOnClickListener{
            showImageAttachMenu()
        }
    }

    private var name=""
    private var phone=""

    private fun validateData() {
        name=binding.nameEt.text.toString().trim()
        phone=binding.phoneEt.text.toString().trim()

        if(name.isEmpty()){
            Toast.makeText(this,"Enter Name",Toast.LENGTH_SHORT).show()

        }else{
            if(imageUri==null){
                updateProfile("")
            }else{
                uploadImage()
            }

        }

    }

    private fun uploadImage() {
         progressdialog.setMessage("Uploading profile image")
        progressdialog.show()

        val filePathAndName ="ProfileImages/"+firebaseAuth.uid
        val reference=FirebaseStorage.getInstance().getReference(filePathAndName)
        reference.putFile(imageUri!!)
            .addOnSuccessListener {taskSnapShot->
                val uriTask: Task<Uri> = taskSnapShot.storage.downloadUrl
                while(!uriTask.isSuccessful);
                val uploadedImageUrl="${uriTask.result}"

                updateProfile(uploadedImageUrl)


            }.addOnFailureListener{e->
                progressdialog.dismiss()
                Toast.makeText(this,"Failed to upload image due to ${e.message}",Toast.LENGTH_SHORT).show()
            }
    }

    private fun updateProfile(uploadedImageUrl: String) {
            progressdialog.setMessage("Updating Profile....")

        val hashmap:HashMap<String, Any> = HashMap()
        hashmap["name"]="${name}"
        hashmap["phone"]="${phone}"
        if(imageUri!=null){
            hashmap["photo"]=uploadedImageUrl
        }

        val reference = FirebaseDatabase.getInstance().getReference("user")
        reference.child(firebaseAuth.uid!!)
            .updateChildren(hashmap)
            .addOnSuccessListener {
                Toast.makeText(this,"Profile Updated",Toast.LENGTH_SHORT).show()

            }.addOnFailureListener { e->
                Toast.makeText(this,"Failed to upload profile due to ${e.message}",Toast.LENGTH_SHORT).show()

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

                    binding.nameEt.setText(name)

                    binding.phoneEt.setText(phone)

                    //set image
                    try{
                        Glide.with(this@ProfileActivity).load(photo).placeholder(R.drawable.ic_baseline_person_24).into(binding.profilepic)
                    }catch (e: Exception){

                    }



                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })
    }


    private fun checkUser() {
        val firebaseUser=firebaseAuth.currentUser
        if(firebaseUser!=null){
            val email=firebaseUser.email


        }else{
            startActivity(Intent(this,LoginActivity::class.java))
            finish()
        }
    }

    private fun showImageAttachMenu(){
        val popupMenu=PopupMenu(this,binding.profilepic)
        popupMenu.menu.add(Menu.NONE,0,0,"Camera")
        popupMenu.menu.add(Menu.NONE,1,1,"Gallery")
        popupMenu.show()

        popupMenu.setOnMenuItemClickListener { item->
            val id=item.itemId
            if(id==0){
                pickImageCamera()
            }else if(id==1){

                pickImageGallery()
            }
            true

        }
    }

    private fun pickImageGallery() {

        val intent=Intent(Intent.ACTION_PICK)
        intent.type="image/*"

        galleryActivityResultLauncher.launch(intent)

    }

    private fun pickImageCamera() {
       val values=ContentValues()
        values.put(MediaStore.Images.Media.TITLE,"Temp_Title")
        values.put(MediaStore.Images.Media.DESCRIPTION,"Temp_Description")

        imageUri=contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,values)

        val intent=Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        intent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri)
        cameraActiviyResultLauncher.launch(intent)

    }

    private val cameraActiviyResultLauncher=registerForActivityResult(
        ActivityResultContracts.StartActivityForResult(),
        ActivityResultCallback<ActivityResult> {result ->

            if(result.resultCode== Activity.RESULT_OK){
                val data =result.data
                //imageUri=data!!.data

                binding.profilepic.setImageURI(imageUri)

            }else{
                Toast.makeText(this,"Cancelled",Toast.LENGTH_SHORT).show()
            }
        }
    )

    private val galleryActivityResultLauncher=registerForActivityResult(
        ActivityResultContracts.StartActivityForResult(), ActivityResultCallback<ActivityResult> {result->
            if(result.resultCode==Activity.RESULT_OK){
                val data=result.data
                imageUri=data!!.data

                binding.profilepic.setImageURI(imageUri)
            }else{
                Toast.makeText(this,"Cancelled",Toast.LENGTH_SHORT).show()

            }

        }
    )


}