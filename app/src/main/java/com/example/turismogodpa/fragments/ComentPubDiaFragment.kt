package com.example.turismogodpa.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.turismogodpa.R
import com.example.turismogodpa.adapter.ComentPubAdapter
import com.example.turismogodpa.data.ComentPubData

class ComentPubDiaFragment : DialogFragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflar el diseño del fragmento del diálogo
        return inflater.inflate(R.layout.fragment_comentpub_dialog, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val rvComentPubDialog: RecyclerView = view.findViewById(R.id.rvComentPubDialog)
        rvComentPubDialog.layoutManager = LinearLayoutManager(requireContext())
        rvComentPubDialog.adapter = ComentPubAdapter(getData())
    }
    private fun getData(): List<ComentPubData> {
        // Aquí puedes obtener y devolver tu lista de datos
        return listOf(
            ComentPubData("¡Increíble experiencia! Los caballos eran dóciles y los paisajes impresionantes. Repetiría sin dudarlo."),
            ComentPubData("Gran paseo a caballo. El guía fue amable y el recorrido espectacular. Muy recomendado."),
            ComentPubData("Gran paseo a caballo. El guía fue amable y el recorrido espectacular. Muy recomendado.")
        )
    }
}