package com.example.turismogodpa.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.turismogodpa.R
import com.example.turismogodpa.data.UserHistEmpData

class UserHistEmpAdapter(private var lstUserEmpHist: List<UserHistEmpData>): RecyclerView.Adapter<UserHistEmpAdapter.ViewHolder>() {

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val tvNomHistUser: TextView = itemView.findViewById(R.id.tvNomHistUser)
        val tvApeHistUser: TextView = itemView.findViewById(R.id.tvApeHistUser)
        val tvDniHistUser: TextView = itemView.findViewById(R.id.tvDniHistUser)
        val tvMailHistUser: TextView = itemView.findViewById(R.id.tvMailHistUser)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(layoutInflater.inflate(R.layout.item_hist_user_emp, parent, false))
    }

    override fun getItemCount(): Int {
        return lstUserEmpHist.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val itemUserHist = lstUserEmpHist[position]
        holder.tvNomHistUser.text = itemUserHist.noUserHist
        holder.tvApeHistUser.text = itemUserHist.apeUserHist
        holder.tvDniHistUser.text = itemUserHist.dniUserHist
        holder.tvMailHistUser.text = itemUserHist.mailUserHist

    }
}