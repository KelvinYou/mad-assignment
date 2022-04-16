package com.example.assignment.models

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.assignment.data.Tutorial
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObjects
import com.google.firebase.ktx.Firebase

class TutorialViewModel : ViewModel() {
    private val col = Firebase.firestore.collection("Tutorials")
    private var tutorial = listOf<Tutorial>()
    private val a = MutableLiveData<List<Tutorial>>()
    private var field = ""
    private var reverse = false

    init {
        col.addSnapshotListener { value, error ->
            if (value == null) return@addSnapshotListener
            tutorial = value.toObjects<Tutorial>()
            a.value = tutorial
        }
    }

    fun getAll() = a

    fun get (id : String) : Tutorial? {
        return a.value?.find { tutorial -> tutorial.id == id }
    }

    fun delete(id: String) {
        col.document(id).delete()
    }

    fun deleteAll() {
        a.value?.forEach { tutorial -> delete(tutorial.id) }
    }

    fun set(tutorial : Tutorial){
        col.document(tutorial.id).set(tutorial, SetOptions.merge())
    }

    fun insert(tutorial: Tutorial){
        col.add(tutorial)
    }

    fun search(title : String?) {
        if (title != null) {
            var list = tutorial

            list = list.filter { l ->
                l.title.contains(title,true)
            }

            a.value = list
        }
    }

    fun sort(field: String): Boolean {
        reverse = if (this.field == field) !reverse else false
        this.field = field

        if(field != null){
            var list = tutorial

            list = when (field) {
                "createdDate"    -> list.sortedBy { f -> f.modifiedDate}
                else    -> list
            }

            if (reverse) list = list.reversed()

            a.value = list
        }
        return reverse
    }
}