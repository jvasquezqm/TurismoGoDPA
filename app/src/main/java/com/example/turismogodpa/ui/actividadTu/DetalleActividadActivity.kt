package com.example.turismogodpa.ui.actividadTu

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.turismogodpa.R
import com.example.turismogodpa.ui.user.reservas.ReservarActivity

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
        //val tvDateActivity= findViewById<TextView>(R.id.tvDuracionActividad)


        val tvRecName: String = intent.extras?.getString("nameActivity").orEmpty()
        tvNameActividad.text = "$tvRecName"

        val tvRecDesc: String = intent.extras?.getString("descriptionActivity").orEmpty()
        tvDescActividad.text = "Descripción de la actividad: $tvRecDesc"

        val tvRecType: String = intent.extras?.getString("typeActivity").orEmpty()
        tvTypeActividad.text = "   $tvRecType"

        val ivRecImag: Int = intent.extras?.getInt("imageActivity") ?: 0
        ivImagActividad.setImageResource(ivRecImag)

        val ivVolverDA: ImageView = findViewById(R.id.ivVolverDA)
        val tvVolverDA: TextView = findViewById(R.id.tvVolverDA)

        //val tvRecDate: String = intent.extras?.getString("dateActivity").orEmpty()
        //tvDateActivity.text = "$tvRecDate"

        btnReservar.setOnClickListener {

            //Este boton me llavará al activity ReservarActivity.
            val intent = Intent(this, ReservarActivity::class.java)
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