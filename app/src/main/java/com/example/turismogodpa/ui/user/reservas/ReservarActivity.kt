package com.example.turismogodpa.ui.user.reservas

import android.annotation.SuppressLint
import android.app.Dialog
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
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.lifecycleScope
import com.example.turismogodpa.R
import com.example.turismogodpa.data.model.UserProfile
import com.example.turismogodpa.ui.autentication.dataStore
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ReservarActivity : AppCompatActivity() {
    private var idUser: String? = null
    private var nameUser: String? = null
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
        val tvRFechad: TextView = findViewById(R.id.tvRFechad)
        val tvRHorad: TextView = findViewById(R.id.tvRHorad)
        val btReservar: Button = findViewById(R.id.btReservar)
        val ivVolverR: ImageView = findViewById(R.id.ivVolverR)
        val tvVolverR: TextView = findViewById(R.id.tvVolverR)
        val etRNombreContacto: TextView = findViewById(R.id.etRNombreContacto)
        val etRTelefono: TextView = findViewById(R.id.etRTelefono)

        //Obtener ID de usuario
        lifecycleScope.launch(Dispatchers.IO) {
            getUserProfile().collect {
                withContext(Dispatchers.Main) {
                    idUser = it.userId
                    println("ID USUARIO EN ACTIVIDAD RESERVA: $idUser")
                    nameUser = it.name
                    etRNombreContacto.text = nameUser
                }
            }
        }

        //Obtener ID de actividad
        val idAct = intent.getStringExtra("idActivity")
        println("ID ACTIVIDAD EN ACTIVIDAD RESERVA: $idAct")

        //Obtener Fecha y hora de actividad
        val tvTime = intent.getStringExtra("timeActivity")
        val parts = tvTime?.split(" ")
        val fecha = parts?.get(0)
        val hora = parts?.get(1)
        tvRFechad.text = fecha
        tvRHorad.text = hora

        //Spinner Numero Personas
        ArrayAdapter.createFromResource(
            this,
            R.array.NumPersonas_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spRNumPersonas.adapter = adapter
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

        //Boton Reservar
        btReservar.setOnClickListener {
            if(spRNumPersonasValue == "" || fecha == "" || hora == ""){
                Log.i("Reserva", "Faltan datos")
            } else {
                if(idUser != "" && idAct != ""){
                    val db = FirebaseFirestore.getInstance()
                    val reserva = hashMapOf(
                        "users" to "$idUser",
                        "actividad" to "$idAct",
                        "telefono" to etRTelefono.text.toString(),
                        "contacto" to etRNombreContacto.text.toString(),
                        "personas" to spRNumPersonasValue,
                        "fecha" to fecha,
                        "hora" to hora,
                        "estado" to true // true = Reservado, false = Cancelado
                    )

                    // Agregar reserva y actualizar el array en la colección "activities"
                    db.collection("bookings")
                        .add(reserva)
                        .addOnSuccessListener { documentReference ->
                            Log.i("Reserva", "DocumentSnapshot added with ID: ${documentReference.id}")

                            // Actualizar el array en la colección "activities"
                            db.collection("activities").document(idAct!!)
                                .update("users", FieldValue.arrayUnion(idUser))
                                .addOnSuccessListener {

                                    Log.i("Activities", "idUser added to array successfully")

                                }
                                .addOnFailureListener { e ->
                                    Log.e("Activities", "Error updating document", e)
                                }
                        }
                        .addOnFailureListener { e ->
                            Log.e("Reserva", "Error adding document", e)
                        }
                    val dialog = Dialog(this)
                    dialog.setContentView(R.layout.dialog_reserva_ok) // Use the correct layout for the dialog

                    val btDialogR: Button = dialog.findViewById(R.id.btnOkDialogReserva)
                    dialog.show()

                    btDialogR.setOnClickListener {
                        dialog.dismiss()
                        val intent = Intent(this, ReservarActivity::class.java)
                        startActivity(intent)
                    }


                }

                else {
                    Log.i("Reserva", "Faltan datos")
                }
            }
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

    private fun getUserProfile() = dataStore.data.map { preferences ->
        UserProfile(
            name = preferences[stringPreferencesKey("name")].orEmpty(),
            email = preferences[stringPreferencesKey("email")].orEmpty(),
            userId = preferences[stringPreferencesKey("userId")].orEmpty()
        )
    }
}
