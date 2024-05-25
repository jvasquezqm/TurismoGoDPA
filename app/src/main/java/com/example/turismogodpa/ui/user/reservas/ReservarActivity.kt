package com.example.turismogodpa.ui.user.reservas

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.turismogodpa.R

class ReservarActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_reservar)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val spRNumPersonas: Spinner = findViewById(R.id.spRNumPersonas)
        val spRFecha: Spinner = findViewById(R.id.spRFecha)
        val spRHora: Spinner = findViewById(R.id.spRHora)
        val btReservar: Button = findViewById(R.id.btReservar)
        val ivVolverR: ImageView = findViewById(R.id.ivVolverR)
        val tvVolverR: TextView = findViewById(R.id.tvVolverR)

        //Spinner Numero Personas
        ArrayAdapter.createFromResource(
            this,
            R.array.NumPersonas_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spRNumPersonas.adapter = adapter
        }

        //Spinner Fecha
        ArrayAdapter.createFromResource(
            this,
            R.array.FechaReservas_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spRFecha.adapter = adapter
        }

        //Spinner Hora
        ArrayAdapter.createFromResource(
            this,
            R.array.HoraReservas_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spRHora.adapter = adapter
        }

        var spRNumPersonasValue: String = ""
        var spRFechaValue: String = ""
        var spRHoraValue: String = ""

        //Llenar Spinner Numero Personas
        spRNumPersonas.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
                spRNumPersonasValue = parent?.getItemAtPosition(position).toString()
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }

        //Llenar Spinner Fecha
        spRFecha.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
                spRFechaValue = parent?.getItemAtPosition(position).toString()
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }

        //Llenar Spinner Hora
        spRHora.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
                spRHoraValue = parent?.getItemAtPosition(position).toString()
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }

        //Boton Reservar
        btReservar.setOnClickListener {
            val intent = Intent(this, ConfirmaReservaActivity::class.java)
            startActivity(intent)
        }

        //Boton Volver
        ivVolverR.setOnClickListener {
            btVolver()
        }
        tvVolverR.setOnClickListener {
            btVolver()
        }
    }

    //Boton Volver
    private fun btVolver() {
        onBackPressed()
    }
}