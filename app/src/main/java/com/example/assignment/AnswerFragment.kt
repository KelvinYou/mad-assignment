package com.example.assignment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.assignment.databinding.FragmentAnswerBinding

class AnswerFragment : Fragment() {
    private var _binding: FragmentAnswerBinding? =null
    private val binding get()=_binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding= FragmentAnswerBinding.inflate(inflater,container,false)

        binding.btnViewAllQuestion.setOnClickListener(){
            val intent = Intent (activity, AllQuestions::class.java)
            activity?.startActivity(intent)
        }

        //binding.btnViewComments.setOnClickListener(){
        //    val intent = Intent (activity, UserOwnComment::class.java)
        //    activity?.startActivity(intent)
        //}

        return binding.root
    }
}