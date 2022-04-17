package com.example.assignment.tutorial

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.assignment.R

import android.content.Intent
import android.media.CamcorderProfile.getAll
import android.util.Log
import android.widget.SearchView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.example.assignment.data.Tutorial
import com.example.assignment.data.TutorialAdapter
import com.example.assignment.data.SearchTutorialAdapter
import com.example.assignment.databinding.FragmentSearchTutorialBinding
import com.example.assignment.tutorial.TutorialDetailActivity
import com.example.assignment.models.TutorialViewModel


class SearchTutorialFragment : Fragment() {

    lateinit var binding: FragmentSearchTutorialBinding
    val tutorial: TutorialViewModel by activityViewModels()
    lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_search_tutorial, container, false)

        val email = Firebase.auth.currentUser?.email



        binding.svSearchTutorial.setOnQueryTextListener(object :
            SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                updateView(p0, email!!)
                return true
            }

            override fun onQueryTextChange(title: String?): Boolean = true

        })


        return binding.root
    }
//
    private fun updateView(text: String?, email: String) {
        val recyclerView = binding.searchRecycleView


        if (text != null) {
            tutorial.getAll().observe(viewLifecycleOwner) {
                val list =
                    it.filter { a -> a.title.contains(text, true) && a.status == "Posted" }

                val adapter = SearchTutorialAdapter(list)

                adapter.setOnItemClickListener(object :
                    SearchTutorialAdapter.onItemClickedListener {

                    override fun onItemClick(position: Int, id: String, type: String) {
//                                Toast.makeText(activity, "ID : $id | Type : $type", Toast.LENGTH_SHORT).show()
                        val intent = Intent(activity, TutorialDetailActivity::class.java)
                            .putExtra("id", id)
                        startActivity(intent)
                    }
                })

                recyclerView.adapter = adapter
                recyclerView.setHasFixedSize(true)

            }
        }
    }

    override fun onPause() {
        super.onPause()
        binding.svSearchTutorial.setQuery("",false)
        binding.svSearchTutorial.clearFocus()
    }


}