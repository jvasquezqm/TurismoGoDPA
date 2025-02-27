package com.example.turismogodpa.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.turismogodpa.PubDetalleEmpActivity
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
        holder.tvTituloHis.text = itemPubHist.titulo
        holder.tvFechaHist.text = itemPubHist.time
        holder.tvTipoHist.text = itemPubHist.type
        holder.tvEstadoHist.text = itemPubHist.state


       /* holder.btOpenPubHist.setOnClickListener {
            listener.onImageButtonClick(itemPubHist.uid)
        }*/
        holder.btOpenPubHist.setOnClickListener {
            val intent = Intent(holder.itemView.context, PubDetalleEmpActivity::class.java)
            intent.putExtra("UID", itemPubHist.uid)
            holder.itemView.context.startActivity(intent)
        }

    }

    interface OnImageButtonClickListener {
        fun onImageButtonClick(uid: String)
    }
}
