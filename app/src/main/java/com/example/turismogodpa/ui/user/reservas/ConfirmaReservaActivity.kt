package com.example.turismogodpa.ui.user.reservas

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.turismogodpa.R
import com.example.turismogodpa.ui.autentication.LoginActivity

class ConfirmaReservaActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_confirma_reserva)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val ivVolver: ImageView = findViewById(R.id.ivVolver)
        val tvVolver: TextView = findViewById(R.id.tvVolver)
        val tvRRIdUsuario: TextView = findViewById(R.id.tvRRIdUsuario)
        val tvRRNombreContacto: TextView = findViewById(R.id.tvRRNombreContacto)
        val tvRRActividad: TextView = findViewById(R.id.tvRRActvidad)
        val tvRREmpresa: TextView = findViewById(R.id.tvRREmpresa)
        val tvRRFecha: TextView = findViewById(R.id.tvRRFecha)
        val btConfirmarRR: Button = findViewById(R.id.btConfirmarRR)


        btConfirmarRR.setOnClickListener {
            val dialog = Dialog(this)
            dialog.setContentView(R.layout.dialog_reserva_ok) // Use the correct layout for the dialog

            val btDialogR: Button = dialog.findViewById(R.id.btnOkDialogReserva)
            dialog.show()

            btDialogR.setOnClickListener {
                dialog.dismiss()
                val intent = Intent(this@ConfirmaReservaActivity, ReservarActivity::class.java)
                startActivity(intent)
            }
        }


        ivVolver.setOnClickListener {
            btVolver()
        }
        tvVolver.setOnClickListener {
            btVolver()
        }

        tvRRIdUsuario.text = "pguerrero@peru.com"
        tvRRNombreContacto.text = "Paolo Guerrero"
        tvRRActividad.text = "Caminata"
        tvRREmpresa.text = "Turismo Peru"
        tvRRFecha.text = "2021-09-30"

    }

    private fun btVolver() {
        onBackPressed()
    }
}