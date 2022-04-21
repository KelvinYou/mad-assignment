package com.example.assignment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.assignment.data.Tutorial
import com.example.assignment.data.TutorialAdapter
import com.example.assignment.models.toBitmap
import com.google.firebase.database.FirebaseDatabase
import java.text.SimpleDateFormat

class CommentListAdapter (private val commentList: List<Comment>): RecyclerView.Adapter<CommentListAdapter.ViewHolder>() {
    //var realtimeCommentDB = FirebaseDatabase.getInstance().getReference("Answer")

    private lateinit var mListener : onItemClickedListener

    interface onItemClickedListener {
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: onItemClickedListener) {
        mListener = listener
    }

    inner class ViewHolder(commentView: View, listener: onItemClickedListener): RecyclerView.ViewHolder(commentView){
        var tvUserName: TextView = commentView.findViewById(R.id.tvUserName)
        var commentDate: TextView = commentView.findViewById(R.id.commentDate)
        var userCommentDetail: TextView = commentView.findViewById(R.id.userCommentDetail)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.comment_display, parent, false)
        //val v = LayoutInflater.from(parent.context).inflate(R.layout.comment_display, parent, false)
        //return ViewHolder(v)
        return ViewHolder(itemView, mListener)
    }

    override fun onBindViewHolder(holder: CommentListAdapter.ViewHolder, position: Int) {
        val currentPosition = commentList[position]

        holder.tvUserName.text = currentPosition.ansIdName
        holder.commentDate.text = currentPosition.ansDate
        holder.userCommentDetail.text = currentPosition.ansComment
    }

    override fun getItemCount(): Int {
        return commentList.size
    }


}