package com.example.assignment.models

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObjects
import com.google.firebase.ktx.Firebase
import com.example.assignment.data.UserProfile

class UserViewModel : ViewModel() {
    private val col = Firebase.firestore.collection("User")
    private val users = MutableLiveData<List<UserProfile>>()

    init {
        col.addSnapshotListener { value, error -> users.value = value?.toObjects<UserProfile>() }
    }

    fun getAll() = users

    fun get(email: String?) : UserProfile? {
        return users.value?.find { user -> user.email == email }
    }

    fun delete(email: String) {
        col.document(email).delete()
    }

    fun deleteAll() {
        users.value?.forEach { user -> delete(user.email) }
    }

    fun set(user: UserProfile){
        col.document(user.email).set(user, SetOptions.merge())
    }

    fun emailExists(email:String) : Boolean {
        return users.value?.any { user -> user.email == email } ?: false
    }


    fun validation(user:UserProfile?, option: Int = 0, confirmPassword:String?,existingPassword:String?, newPassword : String? ): String? {
        val phoneFormat : Regex = Regex("[0-9]{3}-[0-9]{7,10}")
        when(option) {
            0 -> {
                val emailFormat : Regex = Regex("[a-zA-Z0-9._-]+@[a-zA-Z0-9]+\\.+[a-z]+")
                return if (user?.email == "" || user?.password == "" || confirmPassword == "" || user?.ic == "" || user?.phone == ""){
                    "Please do not leave any fields empty"
                }else if (!user?.email?.matches(emailFormat)!!){
                    "Invalid Email Format"
                }else if (!user?.phone?.matches(phoneFormat)!!){
                    "Invalid Phone Format (XXX-XXXXXXX)"
//                }else if (user?.ic.length!=12) {
//                    "IC Number Wrong"
//                }else if (user?.password.length < 6){
//                    "Password should be at least 6 characters"
                }else if (confirmPassword!=user.password){
                    "Password and Confirm Password must be same"
                }else if(emailExists(user.email)){
                    "Email already exist"
                }
                else {
                    null
                }
            }
            1 -> {
                return if(existingPassword == "" || newPassword == "" || confirmPassword == ""){
                    "Please do not leave any fields empty"
                }else if (user?.password!=existingPassword){
                    "Wrong existing password"
                }else if (confirmPassword != newPassword){
                    "Password and Confirm Password must be same"
                }else{
                    null
                }
            }
            2 -> {
                return if (user?.phone == ""){
                    "Please don't leave the phone or gender empty"
                }else if (!user?.phone?.matches(phoneFormat)!!){
                    "Invalid Phone Format (XXX-XXXXXXX)"
                }else {
                    null
                }
            }
        }
        return null
    }

}