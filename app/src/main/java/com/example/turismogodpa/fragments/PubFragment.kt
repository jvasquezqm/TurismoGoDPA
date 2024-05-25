package com.example.turismogodpa.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.turismogodpa.R
import com.example.turismogodpa.adapter.PubAdapter
import com.example.turismogodpa.data.PubResumData


class PubFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_pub, container, false)
        val btAddPub: Button = view.findViewById(R.id.btAddPub)
        //val fragmentContainer: ViewGroup = view.findViewById(R.id.fragment_container)

        val rvPublicaciones: RecyclerView = view.findViewById(R.id.rvPublicaciones)

        rvPublicaciones.layoutManager = LinearLayoutManager(requireContext())
        rvPublicaciones.adapter = PubAdapter(PubList())

       /* btAddPub.setOnClickListener {
            // Inflar la nueva vista y añadirla al contenedor
            val newView = inflater.inflate(R.layout.fragment_add_pub_emp, fragmentContainer, false)
            fragmentContainer.removeAllViews() // Eliminar cualquier vista previa en el contenedor
            fragmentContainer.addView(newView)
            fragmentContainer.visibility = View.VISIBLE
            rvPublicaciones.visibility = View.GONE // Ocultar RecyclerView si es necesario
        }*/

        return view

    }
    private fun PubList(): List<PubResumData>{
        val lstPub: ArrayList<PubResumData> = ArrayList()

        lstPub.add(
            PubResumData(R.drawable.paseocaballo
                , "Paseo a Caballo"
                , "Resumen de paseo a caballo bla bla"
                )
        )

        lstPub.add(
            PubResumData(R.drawable.museolima
                , "A Rite of Passage"
                , "Dream Theater"
                )
        )


        return lstPub

    }
}