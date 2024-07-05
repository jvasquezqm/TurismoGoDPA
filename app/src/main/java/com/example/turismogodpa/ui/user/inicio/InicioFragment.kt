package com.example.turismogodpa.ui.user.inicio

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.content.ContextCompat
import androidx.core.view.isEmpty
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.turismogodpa.R
import com.example.turismogodpa.adapter.ActividadHomeAdapter
import com.example.turismogodpa.data.model.ActividadesHomeModel
import com.example.turismogodpa.databinding.ActivityInicioGeneralBinding
import com.example.turismogodpa.databinding.FragmentInicioBinding
import com.example.turismogodpa.ui.actividadTu.DetalleActividadActivity
import com.example.turismogodpa.ui.autentication.LoginActivity
import com.google.firebase.firestore.FirebaseFirestore

class InicioFragment : Fragment() {
    private lateinit var binding: FragmentInicioBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val View: View= inflater.inflate(R.layout.fragment_inicio, container, false)
        binding = FragmentInicioBinding.bind(View)

        var rvActivides = binding.rvActividadesFiltradas
        var lstActividades : List<ActividadesHomeModel> = listOf()
        loadActivityTypes()



        val db = FirebaseFirestore.getInstance()
        db.collection("activitieshome")
            .addSnapshotListener { value, error ->
                if (error != null) {
                    return@addSnapshotListener
                }
                lstActividades = value!!.documents.map {
                    ActividadesHomeModel(
                        it["image"].toString(),
                        it["name"].toString(),
                        it["description"].toString(),
                        it["date"].toString(),
                        it["type"].toString()
                    )
                }
                rvActivides.layoutManager = LinearLayoutManager(requireContext())
                rvActivides.adapter = ActividadHomeAdapter(lstActividades, object : ActividadHomeAdapter.OnItemClickListener {
                    override fun onItemClick(actividad: ActividadesHomeModel) {
                        val intent = Intent(requireContext(), DetalleActividadActivity::class.java)
                        intent.putExtra("imageActivity", actividad.image)
                        intent.putExtra("nameActivity", actividad.name)
                        intent.putExtra("descriptionActivity", actividad.description)
                        intent.putExtra("typeActivity", actividad.type)
                        intent.putExtra("dateActivity", actividad.date)
                        requireContext().startActivity(intent)

                    }
                })
                val deco = DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
                deco.setDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.divider) ?: requireContext().resources.getDrawable(R.drawable.divider, requireContext().theme))
                rvActivides.addItemDecoration(deco)
                events()
            }


        return View

    }






    private fun events() = with(binding) {


        btBuscarF.setOnClickListener {
            val etBuscar = etBuscar.text.toString()
            val etFecha = etFechaFiltrada.text.toString()
            val spTipoActividad = spTipoActividad.selectedItem.toString()
            val recyclerView: RecyclerView = rvActividadesFiltradas
            val db = FirebaseFirestore.getInstance()
            var lstActividadesFiltradas: List<ActividadesHomeModel> = listOf()
            db.collection("activitieshome")
                .addSnapshotListener { value, error ->
                    if (error != null) {
                        return@addSnapshotListener
                    }
                    lstActividadesFiltradas = value!!.documents.map {
                        ActividadesHomeModel(
                            it["image"].toString(),
                            it["name"].toString(),
                            it["description"].toString(),
                            it["date"].toString(),
                            it["type"].toString()
                        )
                    }
                    recyclerView.layoutManager = LinearLayoutManager(requireContext())
                    recyclerView.adapter = ActividadHomeAdapter(lstActividadesFiltradas.filter { actividad ->
                        actividad.name.contains(etBuscar, ignoreCase = true) && actividad.date.contains(etFecha, ignoreCase = true) && actividad.type.contains(spTipoActividad, ignoreCase = true)
                    }, object : ActividadHomeAdapter.OnItemClickListener {
                        override fun onItemClick(actividad: ActividadesHomeModel) {
                            val intent = Intent(requireContext(), DetalleActividadActivity::class.java)
                            intent.putExtra("imageActivity", actividad.image)
                            intent.putExtra("nameActivity", actividad.name)
                            intent.putExtra("descriptionActivity", actividad.description)
                            intent.putExtra("typeActivity", actividad.type)
                            intent.putExtra("dateActivity", actividad.date)
                            requireContext().startActivity(intent)

                        }
                    })
                    val deco = DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
                    deco.setDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.divider) ?: requireContext().resources.getDrawable(R.drawable.divider, requireContext().theme))
                    recyclerView.addItemDecoration(deco)
                }

        }



    }

    private fun loadActivityTypes() {
        val db = FirebaseFirestore.getInstance()
        db.collection("activitieshome")
            .get()
            .addOnSuccessListener { documents ->
                val types = documents.map { it["type"].toString() }.distinct()
                setupSpinner(types)
            }
            .addOnFailureListener { exception ->
                // Maneja cualquier error aquí
            }
    }
    private fun setupSpinner(types: List<String>) {
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, types)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spTipoActividad.adapter = adapter
    }



}