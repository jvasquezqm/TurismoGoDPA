package com.example.turismogodpa.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.turismogodpa.R
import com.example.turismogodpa.adapter.PubHistAdapter
import com.example.turismogodpa.adapter.UserHistEmpAdapter
import com.example.turismogodpa.data.PubHistData
import com.example.turismogodpa.data.UserHistEmpData


class HistorialFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_historial, container, false)
        val rvPubHist: RecyclerView = view.findViewById(R.id.rvPubHist)
        //val rvHistUser: RecyclerView = view.findViewById(R.id.rvHistUser)

        rvPubHist.layoutManager = LinearLayoutManager(requireContext())
        rvPubHist.adapter = PubHistAdapter(PubHistList())
        //rvHistUser.adapter = UserHistEmpAdapter(HistUserEmpList())

        return view
    }
    private fun PubHistList(): List<PubHistData>{
        val lstPubHist: ArrayList<PubHistData> = ArrayList()

        lstPubHist.add(
            PubHistData( "Paseo Playa"
                , "01/02/20204"
                , "Actividad"
                , "Finalizado"
                )
        )

        lstPubHist.add(
            PubHistData("Visita Ruinas"
                , "28/05/2024"
                , "Excursión"
                , "Activo"
                )
        )

        lstPubHist.add(
            PubHistData("Museo Lima"
                , "01/06/2024"
                , "Actividad"
                , "Cancelado"
                )
        )

        return lstPubHist

    }
/*
    private fun HistUserEmpList(): List<UserHistEmpData>{
        val lstHistUser: ArrayList<UserHistEmpData> = ArrayList()

        lstHistUser.add(
            UserHistEmpData( "Paseo Playa"
                , "01/02/20204"
                , "Actividad"
                , "Finalizado"
            )
        )

        lstHistUser.add(
            UserHistEmpData("Visita Ruinas"
                , "28/05/2024"
                , "Excursión"
                , "Activo"
            )
        )

        lstHistUser.add(
            UserHistEmpData("Museo Lima"
                , "01/06/2024"
                , "Actividad"
                , "Cancelado"
            )
        )

        return lstHistUser

    }
*/

}