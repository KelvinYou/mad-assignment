package com.example.assignment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.RecyclerView
import com.example.assignment.data.TutorialAdapter
import com.example.assignment.databinding.FragmentHomeBinding
import com.example.assignment.models.TutorialViewModel
import com.example.assignment.tutorial.*

class HomeFragment : Fragment() {
    lateinit var binding: FragmentHomeBinding
    val tr : TutorialViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_home, container, false)

        val recyclerView = binding.tutorialList

        tr.getAll().observe(viewLifecycleOwner) { tutoriallist ->

            var newList = tutoriallist.filter { a -> a.status == "Posted" }

            val adapter = TutorialAdapter(newList)
            recyclerView.adapter = adapter
            adapter.setOnItemClickListener(object : TutorialAdapter.onItemClickedListener{
                override fun onItemClick(position: Int, id : String) {
                    val intent = Intent(activity, TutorialDetailActivity::class.java)
                        .putExtra("id", id)
                    startActivity(intent)
                }
            })
            recyclerView.setHasFixedSize(true)
        }

        binding.btnAddTutorial.setOnClickListener{
            val intent = Intent(activity, AddTutorialActivity::class.java)
            startActivity(intent)
        }

        binding.btnSearchTutorial.setOnClickListener{
            val intent = Intent(activity, SearchTutorialFragment::class.java)
            startActivity(intent)
        }

        binding.btnSortDate.setOnClickListener{ sort("createdDate") }

        return binding.root
    }

    private fun sort(field: String) {
        val reverse = tr.sort(field)

        binding.btnSortDate.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)

        val res = if (reverse) R.drawable.ic_down else R.drawable.ic_up

        when (field) {
            "createdDate"    -> binding.btnSortDate.setCompoundDrawablesWithIntrinsicBounds(0, 0, res, 0)
        }
    }
}