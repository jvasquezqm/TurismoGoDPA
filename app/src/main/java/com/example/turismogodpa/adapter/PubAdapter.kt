package com.example.turismogodpa.adapter

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.turismogodpa.PubDetalleEmpActivity
import com.example.turismogodpa.R
import com.example.turismogodpa.data.PubResumData
import com.example.turismogodpa.ui.company.publicacion.UpdatePubEmpActivity
import com.google.firebase.firestore.FirebaseFirestore
import com.squareup.picasso.Picasso

class PubAdapter(private var lstPub: List<PubResumData>, private val listener: PubAdapter.OnImageButtonClickListener): RecyclerView.Adapter<PubAdapter.ViewHolder>() {

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val ivPubEmp: ImageView = itemView.findViewById(R.id.ivPubEmp)
        val tvPubTituloEmp: TextView = itemView.findViewById(R.id.tvPubTituloEmp)
        val tvResumPub: TextView = itemView.findViewById(R.id.tvResumPub)
        val btEditPub: ImageButton = itemView.findViewById(R.id.btEditPub) // Añadir el botón
        val btDeletePub: ImageButton = itemView.findViewById(R.id.btDeletePub) // Añadir el botón de eliminación

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

        holder.btEditPub.setOnClickListener {
            listener.onImageButtonClick(itemPub.documentId)
        }
        holder.btDeletePub.setOnClickListener {
            showDeleteConfirmationDialog(holder.itemView.context, itemPub.documentId)
        }
    }

    private fun showDeleteConfirmationDialog(context: Context, documentId: String) {
        AlertDialog.Builder(context)
            .setTitle("Cancelar Publicación")
            .setMessage("¿Está seguro de que desea cancelar esta publicación?")
            .setPositiveButton("Sí") { dialog, _ ->
                updateStateToCancelled(documentId)
                dialog.dismiss()
            }
            .setNegativeButton("No") { dialog, _ ->
                dialog.dismiss()
            }
            .create()
            .show()
    }
    private fun updateStateToCancelled(documentId: String) {
        val db = FirebaseFirestore.getInstance()
        db.collection("activities").document(documentId)
            .update("state", "Cancelado")
            .addOnSuccessListener {
                // Actualizar la lista local y notificar cambios
                lstPub = lstPub.map { pub ->
                    if (pub.documentId == documentId) {
                        pub.copy(state = "Cancelado")
                    } else {
                        pub
                    }
                }
                notifyDataSetChanged()
            }
            .addOnFailureListener { exception ->
                // Manejar error
            }
    }

    interface OnImageButtonClickListener {
        fun onImageButtonClick(documentId: String)
    }
}