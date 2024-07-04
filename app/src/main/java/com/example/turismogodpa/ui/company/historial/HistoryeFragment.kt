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
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_historial, container, false)
        val db = FirebaseFirestore.getInstance()
        val rvPubHist: RecyclerView = view.findViewById(R.id.rvPubHist)
        var pubHistList: List<PubHistData>


        db.collection("activities")
            .addSnapshotListener{ snap, error ->
                if (error!=null){
                    Log.e("ERROR-FIREBASE", "Detalle del error: ${error.message}")
                    return@addSnapshotListener
                }

                val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

                pubHistList = snap!!.documents.map{document ->
                    val timestamp = document.getTimestamp("time")
                    val formattedDate = timestamp?.toDate()?.let { dateFormat.format(it) } ?: ""
                    val uid = document.id
                    PubHistData(
                        document["titulo"].toString(),
                        //document["time"].toString(),
                        formattedDate,
                        document["type"].toString(),
                        document["state"].toString(),
                        uid

                    )
                }

                rvPubHist.adapter = PubHistAdapter(pubHistList, this)
                rvPubHist.layoutManager = LinearLayoutManager(requireContext())
                }



        return view
    }

    // Implementar el método onImageButtonClick en HistorialFragment
    override fun onImageButtonClick(uid: String) {
        // Log para verificar el UID capturado
        Log.d("HistoryeFragment", "Clicked UID: $uid")

        // Código para abrir PubDetalleEmpActivity
        val intent = Intent(requireContext(), PubDetalleEmpActivity::class.java)
        intent.putExtra("UID", uid)
        startActivity(intent)
    }

}