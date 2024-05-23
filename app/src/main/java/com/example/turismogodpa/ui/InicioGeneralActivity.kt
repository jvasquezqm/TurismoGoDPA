package com.example.turismogodpa.ui

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.turismogodpa.R
import com.example.turismogodpa.adapter.ActividadHomeAdapter
import com.example.turismogodpa.data.model.ActividadesHomeModel

class InicioGeneralActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_inicio_general)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val ActividadHomeAdapter = ActividadHomeAdapter(ListActividades(), object : ActividadHomeAdapter.OnItemClickListener {
            override fun onItemClick(actividad: ActividadesHomeModel) {
                Toast.makeText(this@InicioGeneralActivity, "Seleccionado", Toast.LENGTH_SHORT).show()
            }
        })
        val recyclerView: RecyclerView = findViewById(R.id.rvActividadesH)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = ActividadHomeAdapter

        //Añadir linea de división de los elementos de la lista del recycler view
        var deco = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        deco.setDrawable(getDrawable(R.drawable.divider) ?: resources.getDrawable(R.drawable.divider, theme))
        recyclerView.addItemDecoration(deco)

    }

    private fun ListActividades(): List<ActividadesHomeModel> {
        val lstActividades = ArrayList<ActividadesHomeModel>()
        lstActividades.add(
            ActividadesHomeModel(
                R.drawable.img_actvidad01,
                "Actividad 1",
                " Deporte de riesgo que consiste en tirarse al vacío desde un puente u " +
                        "otro lugar elevado, sujetándose con una cuerda elástica",
                "01/01/2021",
                "Tipo de actividad 1"))


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
                "Tipo de actividad 2"))

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
                "Tipo de actividad 3"))
        return lstActividades

    }
}