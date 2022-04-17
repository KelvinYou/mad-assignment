package com.example.assignment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.FirebaseDatabase

class CommentListAdapter: RecyclerView.Adapter<CommentListAdapter.ViewHolder>() {
    var realtimeCommentDB = FirebaseDatabase.getInstance().getReference("Answer")


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CommentListAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.comment_display, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: CommentListAdapter.ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    inner class ViewHolder(commentView: View): RecyclerView.ViewHolder(commentView){
        var tvUserName: TextView
        var commentDate: TextView
        var userCommentDetail: TextView

        init {
            tvUserName = commentView.findViewById(R.id.tvUserName)
            commentDate = commentView.findViewById(R.id.commentDate)
            userCommentDetail = commentView.findViewById(R.id.userCommentDetail)
        }
    }
}