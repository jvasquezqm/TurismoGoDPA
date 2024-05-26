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

class InicioFragment : Fragment() {
    private lateinit var binding: FragmentInicioBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_inicio, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentInicioBinding.bind(view)

        val recyclerView: RecyclerView = binding.rvActividadesFiltradas
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.actividades_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.spTipoActividad.adapter = adapter
        }
        var spActividadValue: String = ""
        binding.spTipoActividad.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
                spActividadValue = parent?.getItemAtPosition(position).toString()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO()
            }
        }

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = ActividadHomeAdapter(listActividXades(), object : ActividadHomeAdapter.OnItemClickListener {
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
        events()



    }
    private fun listActividXades(): List<ActividadesHomeModel> {
        val lstActividades = ArrayList<ActividadesHomeModel>()
        lstActividades.add(
            ActividadesHomeModel(
                R.drawable.img_actvidad01,
                "Actividad 1",
                " Deporte de riesgo que consiste en tirarse al vacío desde un puente u " +
                        "otro lugar elevado, sujetándose con una cuerda elástica",
                "01/01/2021",
                "Tipo de actividad 1")
        )


        lstActividades.add(
            ActividadesHomeModel(
                R.drawable.img_actividad02,
                "Actividad 2",
                "Machu Picchu es una de las 7 maravillas del mundo más visitadas por los " +
                        "turistas, posee hermosas construcciones a base de piedras, que fueron " +
                        "talladas con mucha precisión y detalle, es la obra más importante para los " +
                        "incas por haber sido construida en una montaña agreste e inaccesible, " +
                        "dividida en dos grandes sectores, urbano y agrícola separados por una gran " +
                        "muralla que desciende por la ladera del cerro hasta llegar a las orillas " +
                        "del rio Vilcanota",
                "02/01/2021",
                "Tipo de actividad 2")
        )

        lstActividades.add(
            ActividadesHomeModel(
                R.drawable.img_actividad03,
                "Actividad 3",
                "El Parque Nacional de Huascarán es un área natural protegida del Perú, " +
                        "ubicada en la Cordillera Blanca, en la región de Áncash. Fue creado el 1 de " +
                        "julio de 1975 y tiene una extensión de 340 000 hectáreas. El parque " +
                        "comprende una gran parte de la Cordillera Blanca, la cadena montañosa más " +
                        "alta de los Andes peruanos y de América tropical. En 1985 fue declarado " +
                        "Patrimonio de la Humanidad por la Unesco",
                "03/01/2021",
                "Tipo de actividad 3")
        )
        return lstActividades

    }

    private fun events() = with(binding) {


        btBuscarF.setOnClickListener {
            val etBuscar = etBuscar.text.toString()
            val etFecha = etFechaFiltrada.text.toString()
            val spTipoActividad = spTipoActividad.selectedItem.toString()
            val recyclerView: RecyclerView = rvActividadesFiltradas
            val lstActividades = listActividXades()
            val lstActividadesFiltradas = lstActividades.filter {
                it.name.contains(etBuscar) && it.date.contains(etFecha) && it.type.contains(spTipoActividad)
            }
            recyclerView.layoutManager = LinearLayoutManager(requireContext())
            recyclerView.adapter = ActividadHomeAdapter(lstActividadesFiltradas, object : ActividadHomeAdapter.OnItemClickListener {
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