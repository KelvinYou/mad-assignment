package com.example.assignment.data

import android.view.View
import android.widget.ImageView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.assignment.R
import com.example.assignment.models.toBitmap
import java.text.SimpleDateFormat

class TutorialAdapter (private val tutorialList: List<Tutorial>) : RecyclerView.Adapter <TutorialAdapter.myViewHolder>() {

    private lateinit var mListener : onItemClickedListener

    interface onItemClickedListener {
        fun onItemClick(position: Int, id : String)
    }
    
    fun setOnItemClickListener(listener: onItemClickedListener) {
        mListener = listener
    }

    class myViewHolder (itemView: View, listener : onItemClickedListener) : RecyclerView.ViewHolder(itemView){

        val tutorialUserName : TextView = itemView.findViewById(R.id.tvuserName)
        val tutorialTitle : TextView = itemView.findViewById(R.id.tvTutorialTitle)
        val tutorialDate : TextView = itemView.findViewById(R.id.tvTutorialDate)
        val tutorialTime : TextView = itemView.findViewById(R.id.tvTutorialTime)
        val tutorialContent : TextView = itemView.findViewById(R.id.tvTutorialContent)
        val tutorialImage : ImageView = itemView.findViewById(R.id.imgTutorialImage)
        val tutorialID : TextView = itemView.findViewById(R.id.tvTutorialID)
        init {
            itemView.setOnClickListener {
                listener.onItemClick(adapterPosition, tutorialID.text.toString() )
            }
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): myViewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.tutorial_list, parent, false)

        return myViewHolder(itemView, mListener)

    }

    override fun onBindViewHolder(holder: myViewHolder, position: Int) {
        val currentPosition = tutorialList[position]

        val words = currentPosition.content.split("\\s+".toRegex())

        val description = ArrayList<String>()

        if(words.size > 33){
            for(i in 0..32){
                description.add(words[i])
            }
            holder.tutorialContent.text = "${description.joinToString(" ")} ..."
        }else {
            holder.tutorialContent.text = currentPosition.content
        }

        holder.tutorialUserName.text = currentPosition.ownerID
        holder.tutorialTitle.text = currentPosition.title
        holder.tutorialDate.text = SimpleDateFormat("dd-MM-yyyy").format(currentPosition.modifiedDate)
        holder.tutorialTime.text = SimpleDateFormat("h:mm aa").format(currentPosition.modifiedDate)
        holder.tutorialImage.setImageBitmap(currentPosition.Image.toBitmap())
        holder.tutorialID.text = currentPosition.id

    }

    override fun getItemCount(): Int {
        return tutorialList.size
    }
}
