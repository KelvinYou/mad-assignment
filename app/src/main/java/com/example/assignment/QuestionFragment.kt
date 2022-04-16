package com.example.assignment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import com.example.assignment.databinding.FragmentQuestionBinding
import com.google.firebase.database.FirebaseDatabase

class QuestionFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val intent = Intent (activity, AskQuestions::class.java)
        activity?.startActivity(intent)

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.activity_ask_questions, container, false)
    }




}

