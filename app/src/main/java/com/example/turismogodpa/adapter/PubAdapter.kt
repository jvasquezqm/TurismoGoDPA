package com.example.turismogodpa.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.turismogodpa.R
import com.example.turismogodpa.data.PubResumData
import com.squareup.picasso.Picasso

class PubAdapter(private var lstPub: List<PubResumData>): RecyclerView.Adapter<PubAdapter.ViewHolder>() {

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val ivPubEmp: ImageView = itemView.findViewById(R.id.ivPubEmp)
        val tvPubTituloEmp: TextView = itemView.findViewById(R.id.tvPubTituloEmp)
        val tvResumPub: TextView = itemView.findViewById(R.id.tvResumPub)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(layoutInflater.inflate(R.layout.item_pub_emp, parent, false))
    }

    override fun getItemCount(): Int {
        return lstPub.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val itemPub = lstPub[position]
        Picasso
            .get()
            .load(itemPub.image)
            .into(holder.ivPubEmp)
        holder.tvPubTituloEmp.text = itemPub.titulo
        holder.tvResumPub.text = itemPub.description


    }
}