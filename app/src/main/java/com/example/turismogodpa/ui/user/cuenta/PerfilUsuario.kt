package com.example.turismogodpa.ui.user.cuenta

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.turismogodpa.R
import com.google.android.material.snackbar.Snackbar

class PerfilUsuario : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_perfil_usuario)
        supportActionBar?.hide()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val btActualizarPerfilUsuario : Button = findViewById(R.id.btActualizarPerfilUsuario)
        val btDesactivarPerfilUsuario : Button = findViewById(R.id.btDesactivarPerfil)


        btActualizarPerfilUsuario.setOnClickListener{
            val rootView : View = findViewById(android.R.id.content)
            Snackbar.make(rootView, "Perfil Actualizado", Snackbar.LENGTH_LONG).show()
        }

        btDesactivarPerfilUsuario.setOnClickListener{
            val rootView : View = findViewById(android.R.id.content)
            Snackbar.make(rootView, "Perfil Desactivado", Snackbar.LENGTH_LONG).show()
        }


    }
}