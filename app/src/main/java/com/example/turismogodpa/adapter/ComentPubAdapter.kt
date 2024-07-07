package com.example.turismogodpa.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.turismogodpa.R
import com.example.turismogodpa.data.ComentPubData

class ComentPubAdapter (private val lstComentPub: List<ComentPubData>) : RecyclerView.Adapter<ComentPubAdapter.MyViewHolder>() {

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvCommentPub: TextView = itemView.findViewById(R.id.tvCommentPub)
        val tvComentNombPub: TextView = itemView.findViewById(R.id.tvComentNombPub)
        val tvComentFechPub: TextView = itemView.findViewById(R.id.tvComentFechPub)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_coment_pub_emp, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val itemComentPub = lstComentPub[position]
        holder.tvComentNombPub.text = itemComentPub.userName
        holder.tvCommentPub.text = itemComentPub.commentText
        holder.tvComentFechPub.text = itemComentPub.timestamp
    }

    override fun getItemCount(): Int {
        return lstComentPub.size
    }
}

