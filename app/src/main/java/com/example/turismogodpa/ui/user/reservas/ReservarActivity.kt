package com.example.turismogodpa.ui.user.reservas

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
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
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.lifecycleScope
import com.example.turismogodpa.R
import com.example.turismogodpa.data.model.UserProfile
import com.example.turismogodpa.ui.autentication.dataStore
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ReservarActivity : AppCompatActivity() {
    private var idUser: String? = null
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_reservar)
        supportActionBar?.hide()
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
        val etRNombreContacto: TextView = findViewById(R.id.etRNombreContacto)

        //Obtener ID de usuario
        lifecycleScope.launch(Dispatchers.IO) {

            getUserProfile().collect {
                withContext(Dispatchers.Main) {
                    idUser = it.userId
                    println("ID USUARIO EN ACTIVIDAD RESERVA: $idUser")
                }

            }
        }

        //Obtener ID de actividad
        val idAct = intent.getStringExtra("idActivity")
        println("ID ACTIVIDAD EN ACTIVIDAD RESERVA: $idAct")

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
//            val intent = Intent(this, ConfirmaReservaActivity::class.java)
//            startActivity(intent)
            if(spRNumPersonasValue == "" || spRFechaValue == "" || spRHoraValue == ""){
                Log.i("Reserva", "Faltan datos")
            }
            else{
                if(idUser != "" && idAct != ""){
//                    Log.i("Reserva", "Datos completos")
//                    Log.i("Reserva", "ID USUARIO: $idUser")
//                    Log.i("Reserva", "ID ACTIVIDAD: $idAct")
//                    Log.i("Reserva", "Numero de Personas: $spRNumPersonasValue")
//                    Log.i("Reserva", "Fecha: $spRFechaValue")
//                    Log.i("Reserva", "Hora: $spRHoraValue")
                    val db = FirebaseFirestore.getInstance()
                    val reserva = hashMapOf(
                        "idUser" to idUser,
                        "idActivity" to idAct,
                        "numPersonas" to spRNumPersonasValue,
                        "fecha" to spRFechaValue,
                        "hora" to spRHoraValue
                    )
                    db.collection("bookings")
                        .add(reserva)
                        .addOnSuccessListener { documentReference ->
                            Log.i("Reserva", "DocumentSnapshot added with ID: ${documentReference.id}")
                        }
                        .addOnFailureListener { e ->
                            Log.e("Reserva", "Error adding document", e)
                        }
                }
                else{
                    Log.i("Reserva", "Faltan datos")
            }}
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
    private fun getUserProfile() = dataStore.data.map{preferences ->
        UserProfile(
            name = preferences[stringPreferencesKey("name")].orEmpty(),
            email = preferences[stringPreferencesKey("email")].orEmpty(),
            userId = preferences[stringPreferencesKey("userId")].orEmpty()

        )

    }
}