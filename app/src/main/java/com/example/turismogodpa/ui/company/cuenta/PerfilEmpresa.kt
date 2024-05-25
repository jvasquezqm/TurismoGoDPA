package com.example.turismogodpa.ui.company.cuenta

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.turismogodpa.R
import com.google.android.material.snackbar.Snackbar

class PerfilEmpresa : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_perfil_empresa)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val btActualizarPerfilEmpresa : Button = findViewById(R.id.btActualizarPerfilEmpresa)
        val btDesactivarPerfilEmpresa : Button = findViewById(R.id.btDesactivarPerfilEmpresa)

        btActualizarPerfilEmpresa.setOnClickListener{
            val  rootView : View = findViewById(android.R.id.content)
            Snackbar.make(rootView,"Perfil Actualizado", Snackbar.LENGTH_LONG).show()
        }

        btDesactivarPerfilEmpresa.setOnClickListener{
            val  rootView : View = findViewById(android.R.id.content)
            Snackbar.make(rootView,"Perfil Dado de Baja", Snackbar.LENGTH_LONG).show()
        }




    }
}