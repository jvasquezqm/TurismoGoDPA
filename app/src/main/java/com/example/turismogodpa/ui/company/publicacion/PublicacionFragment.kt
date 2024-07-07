package com.example.turismogodpa.ui.company.publicacion

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.turismogodpa.AddPubEmpActivity
import com.example.turismogodpa.R
import com.example.turismogodpa.adapter.PubAdapter
import com.example.turismogodpa.data.PubResumData
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.firestore.FirebaseFirestore

class PublicacionFragment : Fragment(), PubAdapter.OnImageButtonClickListener {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_pub, container, false)
        val db = FirebaseFirestore.getInstance()
        val btAddPub: FloatingActionButton = view.findViewById(R.id.btAddPub)
        val rvPublicaciones: RecyclerView = view.findViewById(R.id.rvPublicaciones)
        var pubList: List<PubResumData>

        db.collection("activities")
            .orderBy("titulo")
            .whereEqualTo("state", "Activo")
            .addSnapshotListener{ snap, error ->
                if (error!=null){
                    Log.e("ERROR-FIREBASE", "Detalle del error: ${error.message}")
                    return@addSnapshotListener
                }

                pubList = snap!!.documents.map{document ->
                    PubResumData(
                        document["image"].toString(),
                        document["titulo"].toString(),
                        document["description"].toString(),
                        document.id,
                        document["state"].toString()

                    )
                }
                rvPublicaciones.adapter = PubAdapter(pubList, this)
                rvPublicaciones.layoutManager = LinearLayoutManager(requireContext())
            }


        // Configurar el OnClickListener para el botón flotante
        btAddPub.setOnClickListener {
            val intent = Intent(requireContext(), AddPubEmpActivity::class.java)
            startActivity(intent)
        }


        return view

    }
    override fun onImageButtonClick(documentId: String) {
        val intent = Intent(requireContext(), UpdatePubEmpActivity::class.java)
        intent.putExtra("DOCUMENT_ID", documentId)
        startActivity(intent)
    }

}