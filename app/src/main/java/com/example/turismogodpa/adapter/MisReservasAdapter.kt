package com.example.turismogodpa.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.turismogodpa.R
import com.example.turismogodpa.data.model.MisReservasModel
import com.squareup.picasso.Picasso

class MisReservasAdapter(private var misReservasList: List<MisReservasModel>,
                         private val itemClickListener: OnItemClickListener):
    RecyclerView.Adapter<MisReservasAdapter.ViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(actividad: MisReservasModel)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivImagen: ImageView = itemView.findViewById(R.id.ivMRAct)
        val tvName: TextView = itemView.findViewById(R.id.tvMRTitulo)
        val tvFecha: TextView = itemView.findViewById(R.id.tvMRFecha)
        val tvEstado: TextView = itemView.findViewById(R.id.tvMREstado)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(layoutInflater.inflate(R.layout.item_mis_reservas, parent, false))
    }

    override fun getItemCount(): Int {
        return misReservasList.size
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = misReservasList[position]
        Picasso.get()
            .load(currentItem.image)
            .resize(400, 250)
            .into(holder.ivImagen)
        holder.tvName.text = currentItem.titulo
        holder.tvFecha.text = currentItem.date
        holder.tvEstado.text = currentItem.state

        holder.itemView.setOnClickListener {
            itemClickListener.onItemClick(currentItem)
        }
    }

}
