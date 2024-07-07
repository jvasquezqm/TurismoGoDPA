package com.example.turismogodpa.ui.actividadTu

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.turismogodpa.R
import com.example.turismogodpa.ui.user.reservas.ReservarActivity
import com.google.firebase.firestore.FirebaseFirestore
import com.squareup.picasso.Picasso

class DetalleActividadActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_detalle_actividad)
        supportActionBar?.hide()
        val btnReservar= findViewById<Button>(R.id.btnReservar)
        val tvNameActividad= findViewById<TextView>(R.id.tvNameActividad)
        val tvDescActividad= findViewById<TextView>(R.id.tvDescActividad)
        val tvTypeActividad= findViewById<TextView>(R.id.tvTipoActividad)
        val ivImagActividad= findViewById<ImageView>(R.id.ivImgActividad)
        val tvPrecioActividad = findViewById<TextView>(R.id.tvPrecio)
        val tvNameEmpresa = findViewById<TextView>(R.id.tvNameEmpresa)
        val ratingBar = findViewById<RatingBar>(R.id.ratingBar)
        val tvDuracionActividad= findViewById<TextView>(R.id.tvDuracionActividadD)
        //val tvDateActivity= findViewById<TextView>(R.id.tvDuracionActividad)



        val tvRecName: String = intent.extras?.getString("nameActivity").orEmpty()
        tvNameActividad.text = "$tvRecName"

        val tvRecDesc: String = intent.extras?.getString("descriptionActivity").orEmpty()
        tvDescActividad.text = "Descripción de la actividad: $tvRecDesc"

        val tvRecType: String = intent.extras?.getString("typeActivity").orEmpty()
        tvTypeActividad.text = "   $tvRecType"

        val ivRecImag = intent.getStringExtra("imageActivity")

        Picasso.get()
            .load(ivRecImag)
            .resize(410, 215)
            .into(ivImagActividad)

        val  tvRecPrecio = intent.getStringExtra("priceActivity")
        tvPrecioActividad.text = "S/. $tvRecPrecio"

        val tvRecNameEmpresa = intent.getStringExtra("companyActivity")
        tvNameEmpresa.text = "$tvRecNameEmpresa"

        val idAct = intent.getStringExtra("idActivity")
        println("ID ACTIVIDAD: $idAct")

        val tvRecDuracion = intent.getStringExtra("dateActivity")
        tvDuracionActividad.text = "Fecha: $tvRecDuracion"

        val ivVolverDA: ImageView = findViewById(R.id.ivVolverDA)
        val tvVolverDA: TextView = findViewById(R.id.tvVolverDA)
        //val tvRecDate: String = intent.extras?.getString("dateActivity").orEmpty()
        //tvDateActivity.text = "$tvRecDate"
        //rating bar de la actividad, debe ser un valor entre 0 y 5 estrellas, debe ser el promedio de las calificaciones de los usuarios
        val db = FirebaseFirestore.getInstance()

        btnReservar.setOnClickListener {

            //Este boton me llavará al activity ReservarActivity.
            val intent = Intent(this, ReservarActivity::class.java)
            intent.putExtra("idActivity", idAct)
            intent.putExtra("timeActivity", tvRecDuracion)

            startActivity(intent)


        }

        ivVolverDA.setOnClickListener {
            onBackPressed()
        }
        tvVolverDA.setOnClickListener {
            onBackPressed()
        }

    }
}