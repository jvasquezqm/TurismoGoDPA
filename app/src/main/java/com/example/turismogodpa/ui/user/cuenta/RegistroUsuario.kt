package com.example.turismogodpa.ui.user.cuenta

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.turismogodpa.R
import com.example.turismogodpa.R.id.btRegistroUsuario
import com.google.android.material.snackbar.Snackbar

class RegistroUsuario : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_registro_usuario)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val btRegistroUsuario : Button = findViewById(R.id.btRegistroUsuario)

        btRegistroUsuario.setOnClickListener{

            val  rootView : View = findViewById(android.R.id.content)
            Snackbar.make(rootView,"Bienvenido a Turismo Go App", Snackbar.LENGTH_LONG).show()

        }


    }
}