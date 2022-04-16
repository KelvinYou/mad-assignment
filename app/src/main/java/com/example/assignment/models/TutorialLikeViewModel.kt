package com.example.assignment.models

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObjects
import com.google.firebase.ktx.Firebase
import com.example.assignment.data.TutorialLike

class TutorialLikeViewModel : ViewModel() {
    private val col = Firebase.firestore.collection("TutorialLike")
    private val l = MutableLiveData<List<TutorialLike>>()

    init {
        col.addSnapshotListener { value, error -> l.value = value?.toObjects<TutorialLike>() }
    }

    fun getAll() = l

    fun get(id : String) : TutorialLike? {
        return l.value?.find { like -> like.id == id }
    }

    fun delete(id: String) {
        col.document(id).delete()
    }

    fun deleteAll() {
        l.value?.forEach { like -> delete(like.id) }
    }

    fun set(like : TutorialLike){
        col.document(like.id).set(like)
    }

    fun insert(like: TutorialLike) : Boolean{

        val b = l.value?.find { a -> a.userID == like.userID && a.tutorialID == like.tutorialID }
        if (b != null){
            delete(b.id)
            return false
        }else{
            col.add(like)
            return true
        }
    }

    fun getCountUser(tutorialID: String) : Int {
        var count = 0

        l.value?.forEach { like ->
            if(like.tutorialID == tutorialID){
                count += 1

            }
        }
        Log.e("testing", "${l.value}")
        return count
    }
}