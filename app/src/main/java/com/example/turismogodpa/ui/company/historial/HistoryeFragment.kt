package com.example.turismogodpa.ui.company.historial

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.turismogodpa.PubDetalleEmpActivity
import com.example.turismogodpa.R
import com.example.turismogodpa.adapter.PubHistAdapter
import com.example.turismogodpa.data.PubHistData
import com.example.turismogodpa.data.PubResumData
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class HistoryeFragment : Fragment(), PubHistAdapter.OnImageButtonClickListener  {
    private lateinit var rvPubHist: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_historial, container, false)

        // Inicializar RecyclerView
        rvPubHist = view.findViewById(R.id.rvPubHist)
        rvPubHist.layoutManager = LinearLayoutManager(requireContext())

        // Cargar datos desde Firestore
        loadActivitiesFromFirestore()

        return view
    }

    private fun loadActivitiesFromFirestore() {
        val db = FirebaseFirestore.getInstance()

        db.collection("activities")
            .orderBy("time") // Ordenar por el campo 'time'
            .addSnapshotListener { snap, error ->
                if (error != null) {
                    Log.e("ERROR-FIREBASE", "Detalle del error: ${error.message}")
                    return@addSnapshotListener
                }

                val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

                val pubHistList = snap?.documents?.mapNotNull { document ->
                    val timestamp = document.getTimestamp("time")
                    val formattedDate = timestamp?.toDate()?.let { dateFormat.format(it) } ?: ""
                    val uid = document.id

                    // Crear objeto PubHistData
                    PubHistData(
                        document.getString("titulo") ?: "",
                        formattedDate,
                        document.getString("type") ?: "",
                        document.getString("state") ?: "",
                        uid
                    )
                } ?: emptyList()

                // Configurar adaptador para RecyclerView
                rvPubHist.adapter = PubHistAdapter(pubHistList, this)
            }
    }

    override fun onImageButtonClick(uid: String) {
        Log.d("HistoryeFragment", "Clicked UID: $uid")

        // Abrir PubDetalleEmpActivity
        val intent = Intent(requireContext(), PubDetalleEmpActivity::class.java)
        intent.putExtra("UID", uid)
        startActivity(intent)
    }
}