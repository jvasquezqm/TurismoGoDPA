package com.example.turismogodpa.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.turismogodpa.R
import com.example.turismogodpa.data.PubHistData

class PubHistAdapter(private var lstPubHist: List<PubHistData>, private val listener: PubHistAdapter.OnImageButtonClickListener): RecyclerView.Adapter<PubHistAdapter.ViewHolder>() {

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val tvTituloHis: TextView = itemView.findViewById(R.id.tvTituloHis)
        val tvFechaHist: TextView = itemView.findViewById(R.id.tvFechaHist)
        val tvTipoHist: TextView = itemView.findViewById(R.id.tvTipoHist)
        val tvEstadoHist: TextView = itemView.findViewById(R.id.tvEstadoHist)
        val btOpenPubHist: ImageButton = itemView.findViewById(R.id.btOpenPubHist)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(layoutInflater.inflate(R.layout.item_histpub_emp, parent, false))
    }

    override fun getItemCount(): Int {
        return lstPubHist.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val itemPubHist = lstPubHist[position]
        holder.tvTituloHis.text = itemPubHist.tituloPubHist
        holder.tvFechaHist.text = itemPubHist.fechaPubHist
        holder.tvTipoHist.text = itemPubHist.tipoPubHist
        holder.tvEstadoHist.text = itemPubHist.estadoPubHist


        holder.btOpenPubHist.setOnClickListener {
            listener.onImageButtonClick(position)
        }

    }

    interface OnImageButtonClickListener {
        fun onImageButtonClick(position: Int)
    }
}
