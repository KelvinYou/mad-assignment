package com.example.assignment.data

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.assignment.R
import java.text.SimpleDateFormat

class TutorialCommentAdapter(val fn: (myViewHolder, TutorialComment) -> Unit = { _, _ -> })
    : ListAdapter<TutorialComment, TutorialCommentAdapter.myViewHolder>(DiffCallback) {


        companion object DiffCallback : DiffUtil.ItemCallback<TutorialComment>() {
        override fun areItemsTheSame(a: TutorialComment, b: TutorialComment)    = a.tutorialID == b.tutorialID
        override fun areContentsTheSame(a: TutorialComment, b: TutorialComment) = a == b

    }

    class myViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tutorialCommentUser : TextView = itemView.findViewById(R.id.tvUserName)
        val tutorialCommentDetail : TextView = itemView.findViewById(R.id.userCommentDetail)
        val tutorialCommentDate : TextView = itemView.findViewById(R.id.commentDate)
        val tutorialCommentTime : TextView = itemView.findViewById(R.id.commentTime)
//            var tutorialUserImage: ImageView = itemView.findViewById(R.id.userImage)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): myViewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.tutorial_comment, parent, false)

        return myViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: myViewHolder, position: Int) {
        val currentPosition = getItem(position)

        holder.tutorialCommentUser.text = currentPosition.userID
        holder.tutorialCommentDetail.text = currentPosition.content
        holder.tutorialCommentDate.text = SimpleDateFormat("dd-MM-yyyy").format(currentPosition.date)
        holder.tutorialCommentTime.text = SimpleDateFormat("h:mm aa").format(currentPosition.date)

        fn(holder, currentPosition)
    }
}