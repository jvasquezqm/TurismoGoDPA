package com.example.turismogodpa.ui.user.cuenta

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.turismogodpa.R
import com.example.turismogodpa.R.id.btRegistroUsuario
import com.example.turismogodpa.R.id.etNombreUsuarioReg
import com.google.android.material.snackbar.Snackbar

class RegistroUsuario : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_registro_usuario)
        supportActionBar?.hide()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val etNombreUsuarioReg: EditText = findViewById(R.id.etNombreUsuarioReg)
        val etApellidoUsuarioReg: EditText = findViewById(R.id.etApellidoUsuarioReg)
        val etDniUsuarioReg: EditText = findViewById(R.id.etDniUsuarioReg)
        val etCorreoUsuarioReg: EditText = findViewById(R.id.etCorreoUsuarioReg)
        val etFechaNacimientoUsuarioReg: EditText = findViewById(R.id.etFechaNacimientoUsuarioReg)
        val etTelefonoUsuarioReg: EditText = findViewById(R.id.etTelefonoUsuarioReg)
        val etPasswordUsuarioReg: EditText = findViewById(R.id.etPasswordUsuarioReg)
        val etPassword2UsuarioReg: EditText = findViewById(R.id.etPassword2UsuarioReg)
        val btRegistroUsuario : Button = findViewById(R.id.btRegistroUsuario)


        btRegistroUsuario.setOnClickListener{
            val rootView : View = findViewById(android.R.id.content)
            Snackbar.make(rootView, "Bienvenido a Turismo Go App", Snackbar.LENGTH_LONG).show()
        }
    }
}