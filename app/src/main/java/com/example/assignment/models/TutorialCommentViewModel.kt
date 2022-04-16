package com.example.assignment.models

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObjects
import com.google.firebase.ktx.Firebase
import com.example.assignment.data.TutorialComment

class TutorialCommentViewModel : ViewModel() {
        private val col = Firebase.firestore.collection("Comment")
        private val c = MutableLiveData<List<TutorialComment>>()

        init {
            col.addSnapshotListener { value, error -> c.value = value?.toObjects<TutorialComment>() }
        }

        fun getAll() = c

        fun get(id : String) : TutorialComment? {
            return c.value?.find { comment -> comment.id == id }
        }

        fun delete(id: String) {
            col.document(id).delete()
        }

        fun deleteAll() {
            c.value?.forEach { comment -> delete(comment.id) }
        }

        fun set(comment : TutorialComment){
            col.document(comment.id).set(comment)
        }

        fun insert(comment: TutorialComment){
            col.add(comment)
        }

        fun getCountUser(articleID: String) : Int {
            var count = 0

            c.value?.forEach { comment ->
                if(comment.articleID == articleID){
                    count += 1
                }
            }
            return count
        }
    }