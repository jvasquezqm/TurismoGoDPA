package com.example.turismogodpa.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.turismogodpa.R
import com.example.turismogodpa.data.model.ActividadesHomeModel
import com.squareup.picasso.Picasso

class ActividadHomeAdapter (
    private var lstActividadesH: List<ActividadesHomeModel>,
    private val itemClickListener: OnItemClickListener
): RecyclerView.Adapter<ActividadHomeAdapter.ViewHolder>(){

    interface OnItemClickListener {
        fun onItemClick(actividad: ActividadesHomeModel)
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val ivImage: ImageView = itemView.findViewById(R.id.ivImgActividad)
        val tvName: TextView = itemView.findViewById(R.id.tvNameActividad)
        val tvDescription: TextView = itemView.findViewById(R.id.tvDescActividad)
        val tvDate: TextView = itemView.findViewById(R.id.tvFechaActividad)
        val tvType: TextView = itemView.findViewById(R.id.tvTipoActividad)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(layoutInflater.inflate(
            R.layout.item_actividades_home, parent, false))
    }

    override fun getItemCount(): Int {
        return lstActividadesH.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val actividad = lstActividadesH[position]
        //Importar a Picasso
        Picasso.get()
            .load(actividad.image)
            .resize(550, 350)
            .into(holder.ivImage)
        holder.tvName.text = actividad.name
        holder.tvDescription.text = actividad.description
        holder.tvDate.text = actividad.date
        holder.tvType.text = actividad.type

        holder.itemView.setOnClickListener {
            itemClickListener.onItemClick(actividad)
        }
    }
}