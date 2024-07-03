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

class PublicacionFragment : Fragment() {


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
            .addSnapshotListener{ snap, error ->
                if (error!=null){
                    Log.e("ERROR-FIREBASE", "Detalle del error: ${error.message}")
                    return@addSnapshotListener
                }

                pubList = snap!!.documents.map{document ->
                    PubResumData(
                        document["image"].toString(),
                        document["titulo"].toString(),
                        document["Description"].toString()

                    )
                }
                rvPublicaciones.adapter = PubAdapter(pubList)
                rvPublicaciones.layoutManager = LinearLayoutManager(requireContext())
            }


        // Configurar el OnClickListener para el botón flotante
        btAddPub.setOnClickListener {
            val intent = Intent(requireContext(), AddPubEmpActivity::class.java)
            startActivity(intent)
        }


        return view



    /*private fun PubList(): List<PubResumData>{
        val lstPub: ArrayList<PubResumData> = ArrayList()

        lstPub.add(
            PubResumData(R.drawable.paseocaballo
                , "Paseo a Caballo"
                , "Disfruta de una emocionante aventura explorando los hermosos paisajes naturales mientras cabalgas a través de senderos escénicos en compañía de majestuosos caballos. Nuestra actividad de \"Paseo en Caballo\" te brinda la oportunidad de experimentar la naturaleza de una manera única y emocionante."
            )
        )

        lstPub.add(
            PubResumData(R.drawable.museolima
                , "Paseo Museo Lima"
                , "Sumérgete en un viaje fascinante a través del tiempo con nuestro Paseo Museo Lima. Esta actividad te llevará por los museos más emblemáticos de la ciudad, ofreciéndote una oportunidad única para explorar la rica historia y cultura de Lima.\n" +
                        "\n"
            )
        )


        return lstPub*/

    }


}