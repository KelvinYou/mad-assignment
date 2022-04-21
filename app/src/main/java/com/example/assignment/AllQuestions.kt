package com.example.assignment

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class AllQuestions : AppCompatActivity() {

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var database : DatabaseReference
    private lateinit var quesRecycleView: RecyclerView
    private lateinit var quesArrayList: ArrayList<Questions>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_questions_list)
        firebaseAuth = FirebaseAuth.getInstance()

        quesRecycleView = findViewById(R.id.questionsList)
        quesRecycleView.layoutManager = LinearLayoutManager(this)
        quesRecycleView.setHasFixedSize(true)

        quesArrayList = arrayListOf<Questions>()
        getQuesData()
    }

    private fun getQuesData() {

        database = FirebaseDatabase.getInstance().getReference("Questions")

        database.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    for(quesSnapshot in snapshot.children){

                        val ques = quesSnapshot.getValue(Questions::class.java)
                        quesArrayList.add(ques!!)
                    }

                    var adapter = QuestionListAdapter(quesArrayList)
                    quesRecycleView.adapter = adapter
                    adapter.setOnClickListener(object : QuestionListAdapter.onItemClickListener{
                        override fun onItemClick(position: Int) {
                            val intent = Intent(this@AllQuestions, AnswerActivity::class.java)
                            this@AllQuestions.startActivity(intent)
                        }

                        override fun onItemClick(position: Int, quesTitle: String) {
                            val intent = Intent(this@AllQuestions, AnswerActivity::class.java)
                                .putExtra("quesTitle", quesTitle)
                            this@AllQuestions.startActivity(intent)
                        }

                    })

                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }


        })

    }
}