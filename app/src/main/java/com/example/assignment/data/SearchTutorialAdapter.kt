package com.example.assignment.data
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.assignment.R


class SearchTutorialAdapter (private val tutorialList: List<Tutorial>?) : RecyclerView.Adapter<SearchTutorialAdapter.myViewHolder>() {

    private lateinit var mListener : onItemClickedListener

    interface onItemClickedListener{
        fun onItemClick(position: Int, id : String, type : String)
    }

    fun setOnItemClickListener(listener: onItemClickedListener){
        mListener = listener
    }


    class myViewHolder (itemView: View, listener : onItemClickedListener) : RecyclerView.ViewHolder(itemView) {
        val title : TextView = itemView.findViewById(R.id.tvTitleTutorial)
        val content : TextView = itemView.findViewById(R.id.tvDescriptionTutorial)
        val type : TextView = itemView.findViewById(R.id.tvSearchItemType)
        val id : TextView = itemView.findViewById(R.id.tvSearchItemID)

        init {
            itemView.setOnClickListener {
                listener.onItemClick(adapterPosition, id.text.toString(),type.text.toString())
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): myViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.search_card_item,parent,false)

        return myViewHolder(itemView, mListener)
    }

    override fun onBindViewHolder(holder: myViewHolder, position: Int) {
        if (tutorialList != null){
            val currentPosition = tutorialList[position]

            val words = currentPosition.content.split("\\s+".toRegex())

            val description = ArrayList<String>()

            if(words.size > 30){
                for(i in 0..29){
                    description.add(words[i])
                }
                holder.content.text = description.joinToString(" ")
            }else {
                holder.content.text = currentPosition.content
            }


            holder.title.text = currentPosition.title
            holder.type.text = "Tutorial"
            holder.id.text = currentPosition.id
        }

    }

    override fun getItemCount(): Int {
        return tutorialList?.size ?: 0
    }
}