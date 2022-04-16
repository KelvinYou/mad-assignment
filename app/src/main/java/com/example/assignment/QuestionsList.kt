package com.example.assignment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*

class QuestionsList : AppCompatActivity() {

    private lateinit var database : DatabaseReference
    private lateinit var quesRecycleView: RecyclerView
    private lateinit var quesArrayList: ArrayList<Questions>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_questions_list)

        quesRecycleView = findViewById(R.id.questionsList)
        quesRecycleView.layoutManager = LinearLayoutManager(this)
        quesRecycleView.setHasFixedSize(true)

        quesArrayList = arrayListOf<Questions>()
        getQuesData()

    }

    private fun getQuesData() {

        //var database = FirebaseDatabase.getInstance().reference
        database = FirebaseDatabase.getInstance().getReference("Questions")

        database.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    for(quesSnapshot in snapshot.children){

                        val ques = quesSnapshot.getValue(Questions::class.java)
                        quesArrayList.add(ques!!)
                    }

                    quesRecycleView.adapter = QuestionListAdapter(quesArrayList)

                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }


        })
    }
}