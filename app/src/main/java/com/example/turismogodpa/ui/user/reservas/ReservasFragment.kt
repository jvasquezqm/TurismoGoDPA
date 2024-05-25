package com.example.turismogodpa.ui.user.reservas

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.turismogodpa.R
import com.example.turismogodpa.adapter.MisReservasAdapter
import com.example.turismogodpa.data.model.ActividadesHomeModel
import com.example.turismogodpa.data.model.MisReservasModel
import com.example.turismogodpa.databinding.FragmentReservasBinding
import com.example.turismogodpa.ui.actividadTu.DetalleActividadActivity


class ReservasFragment : Fragment() {

    private lateinit var binding: FragmentReservasBinding

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        // Inflate the layout for this fragment
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        //return inflater.inflate(R.layout.fragment_reservas, container, false)
        //val view: View = inflater.inflate(R.layout.fragment_reservas, container, false)
        //val rvMisReservas: RecyclerView = view.findViewById(R.id.rvMR)
        binding = FragmentReservasBinding.inflate(inflater, container, false)
        val view = binding.root
        val rvMisReservas: RecyclerView = binding.rvMR


        rvMisReservas.layoutManager = LinearLayoutManager(requireContext())
        //rvMisReservas.adapter = MisReservasAdapter(this.misReservasList())
        rvMisReservas.adapter = MisReservasAdapter(misReservasList(), object : MisReservasAdapter.OnItemClickListener {
            override fun onItemClick(currentItem: MisReservasModel) {
                binding.ListaRerserva.visibility = View.GONE
                binding.DetalleReserva.visibility = View.VISIBLE
            }
        })

        val deco = DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
        deco.setDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.divider) ?: requireContext().resources.getDrawable(R.drawable.divider, requireContext().theme))
        rvMisReservas.addItemDecoration(deco)

        binding.tvDRNombre.text = "Actividad 1"
        binding.tvDRFecha.text = "12/12/2021"
        binding.tvDREmpresa.text = "Empresa 1"
        binding.tvDRHora.text = "12:00"




        return view
    }

    private fun misReservasList(): List<MisReservasModel>{
        val misReservasList = ArrayList<MisReservasModel>()
        misReservasList.add(MisReservasModel(R.drawable.img_actvidad01, "Actividad 1", "12/12/2021", "Pendiente"))
        misReservasList.add(MisReservasModel(R.drawable.img_actividad02, "Actividad 2", "12/12/2021", "Pendiente"))
        misReservasList.add(MisReservasModel(R.drawable.img_actividad03, "Actividad 3", "12/12/2021", "Completo"))
        misReservasList.add(MisReservasModel(R.drawable.img_actvidad01, "Actividad 4", "12/12/2021", "Pendiente"))
        misReservasList.add(MisReservasModel(R.drawable.img_actividad02, "Actividad 5", "12/12/2021", "Completo"))
        misReservasList.add(MisReservasModel(R.drawable.img_actividad03, "Actividad 6", "12/12/2021", "Pendiente"))
        misReservasList.add(MisReservasModel(R.drawable.img_actividad02, "Actividad 7", "12/12/2021", "Completo"))
        misReservasList.add(MisReservasModel(R.drawable.img_actividad03, "Actividad 8", "12/12/2021", "Pendiente"))
        misReservasList.add(MisReservasModel(R.drawable.img_actividad02, "Actividad 9", "12/12/2021", "Completo"))
        misReservasList.add(MisReservasModel(R.drawable.img_actividad03, "Actividad 10", "12/12/2021", "Pendiente"))

        return misReservasList
    }


}