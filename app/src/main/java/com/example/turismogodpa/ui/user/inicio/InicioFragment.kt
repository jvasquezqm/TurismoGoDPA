package com.example.turismogodpa.ui.user.inicio

import android.content.Intent
import android.icu.text.SimpleDateFormat
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.turismogodpa.R
import com.example.turismogodpa.adapter.ActividadHomeAdapter
import com.example.turismogodpa.data.model.ActividadesHomeModel
import com.example.turismogodpa.databinding.FragmentInicioBinding
import com.example.turismogodpa.ui.actividadTu.DetalleActividadActivity
import com.google.android.gms.tasks.Tasks
import com.google.firebase.firestore.FirebaseFirestore
import java.util.Locale

class InicioFragment : Fragment() {
    private lateinit var binding: FragmentInicioBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_inicio, container, false)
        binding = FragmentInicioBinding.bind(view)

        var rvActivides = binding.rvActividadesFiltradas
        var lstActividades: List<ActividadesHomeModel> = listOf()
        loadActivityTypes()

        val db = FirebaseFirestore.getInstance()
        db.collection("activities")
            .addSnapshotListener { value, error ->
                if (error != null) {
                    return@addSnapshotListener
                }

                val actividadesList = mutableListOf<ActividadesHomeModel>()
                val tasks = value!!.documents.mapNotNull { document ->
                    val idCompanyRef = document.getDocumentReference("idCompany")
                    idCompanyRef?.get()?.continueWith { task ->
                        val razonSocial = if (task.isSuccessful) {
                            task.result?.getString("razonSocial").toString()
                        } else {
                            // Handle error here
                            ""
                        }
                        val time = document.getTimestamp("time")?.toDate()
                        val formattedTime:String = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(time)
                        //println("TIME: $formattedTime")
                        //esto es lo que se va a mostrar en la lista
                        ActividadesHomeModel(
                            document.id,
                            document["image"].toString(),
                            document["titulo"].toString(),
                            document["description"].toString(),
                            formattedTime,
                            document["type"].toString(),
                            document["price"].toString(),
                            razonSocial
                        )
                    }
                }

                // Wait for all tasks to complete
                Tasks.whenAllComplete(tasks).addOnCompleteListener { completedTasks ->
                    completedTasks.result?.forEach { completedTask ->
                        val actividad = (completedTask as? com.google.android.gms.tasks.Task<ActividadesHomeModel>)?.result
                        if (actividad != null) {
                            actividadesList.add(actividad)
                        }
                    }

                    rvActivides.layoutManager = LinearLayoutManager(requireContext())
                    rvActivides.adapter = ActividadHomeAdapter(actividadesList, object : ActividadHomeAdapter.OnItemClickListener {
                        override fun onItemClick(actividad: ActividadesHomeModel) {
                            val intent = Intent(requireContext(), DetalleActividadActivity::class.java)
                            intent.putExtra("idActivity", actividad.id)
                            intent.putExtra("imageActivity", actividad.image)
                            intent.putExtra("nameActivity", actividad.name)
                            intent.putExtra("descriptionActivity", actividad.description)
                            intent.putExtra("typeActivity", actividad.type)
                            intent.putExtra("dateActivity", actividad.date)
                            intent.putExtra("priceActivity", actividad.price)
                            intent.putExtra("companyActivity", actividad.company)
                            requireContext().startActivity(intent)
                        }
                    })


                    events()
                }
            }
        val deco = DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
        deco.setDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.divider) ?: requireContext().resources.getDrawable(R.drawable.divider, requireContext().theme))
        rvActivides.addItemDecoration(deco)

        return view
    }

    private fun events() = with(binding) {
        btBuscarF.setOnClickListener {
            val etBuscar = etBuscar.text.toString()
            val etFecha = etFechaFiltrada.text.toString()
            val spTipoActividad = spTipoActividad.selectedItem.toString()
            val recyclerView: RecyclerView = rvActividadesFiltradas
            val db = FirebaseFirestore.getInstance()
            db.collection("activities")
                .addSnapshotListener { value, error ->
                    if (error != null) {
                        return@addSnapshotListener
                    }

                    val actividadesList = mutableListOf<ActividadesHomeModel>()
                    val tasks = value!!.documents.mapNotNull { document ->
                        val idCompanyRef = document.getDocumentReference("idCompany")
                        idCompanyRef?.get()?.continueWith { task ->
                            val razonSocial = if (task.isSuccessful) {
                                task.result?.getString("razonSocial").toString()
                            } else {
                                ""
                            }
                            val time = document.getTimestamp("time")?.toDate()
                            val formattedTime:String = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(time)
                            //la cantidad de esta lista debe ser igual
                            ActividadesHomeModel(
                                document.id,
                                document["image"].toString(),
                                document["titulo"].toString(),
                                document["description"].toString(),
                                formattedTime,
                                document["type"].toString(),
                                document["price"].toString(),
                                razonSocial
                            )
                        }
                    }

                    Tasks.whenAllComplete(tasks).addOnCompleteListener { completedTasks ->
                        completedTasks.result?.forEach { completedTask ->
                            val actividad = (completedTask as? com.google.android.gms.tasks.Task<ActividadesHomeModel>)?.result
                            if (actividad != null) {
                                actividadesList.add(actividad)
                            }
                        }

                        val filteredList = actividadesList.filter { actividad ->
                            actividad.name.contains(etBuscar, ignoreCase = true) &&
                                    actividad.date.contains(etFecha, ignoreCase = true) &&
                                    actividad.type.contains(spTipoActividad, ignoreCase = true)
                        }

                        recyclerView.layoutManager = LinearLayoutManager(requireContext())
                        recyclerView.adapter = ActividadHomeAdapter(filteredList, object : ActividadHomeAdapter.OnItemClickListener {
                            override fun onItemClick(actividad: ActividadesHomeModel) {
                                val intent = Intent(requireContext(), DetalleActividadActivity::class.java)
                                intent.putExtra("idActivity", actividad.id)
                                intent.putExtra("imageActivity", actividad.image)
                                intent.putExtra("nameActivity", actividad.name)
                                intent.putExtra("descriptionActivity", actividad.description)
                                intent.putExtra("typeActivity", actividad.type)
                                intent.putExtra("dateActivity", actividad.date)
                                intent.putExtra("priceActivity", actividad.price)
                                intent.putExtra("companyActivity", actividad.company)
                                requireContext().startActivity(intent)
                            }
                        })

                        val deco = DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
                        deco.setDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.divider) ?: requireContext().resources.getDrawable(R.drawable.divider, requireContext().theme))
                        recyclerView.addItemDecoration(deco)
                    }
                }
        }
    }


    private fun loadActivityTypes() {
        val db = FirebaseFirestore.getInstance()
        db.collection("activities")
            .get()
            .addOnSuccessListener { documents ->
                val types = documents.map { it["type"].toString() }.distinct()
                if (isAdded) { // Check if the Fragment is still attached to its Activity
                    setupSpinner(types)
                }
            }
            .addOnFailureListener { exception ->
                // Handle any error here
            }
    }

    private fun setupSpinner(types: List<String>) {
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, types)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spTipoActividad.adapter = adapter
    }
}
