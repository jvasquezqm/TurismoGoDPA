package com.example.turismogodpa.ui.autentication

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.turismogodpa.MainActivity
import com.example.turismogodpa.R
import com.example.turismogodpa.ui.company.cuenta.RegistroEmpresa
import com.example.turismogodpa.ui.user.cuenta.RegistroUsuario
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        supportActionBar?.hide()

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val etEMail: EditText = findViewById(R.id.etEmail)
        val etPass: EditText = findViewById(R.id.etPassword)
        val btnLogin: Button = findViewById(R.id.btnLogin)
        val tvRegister: TextView = findViewById(R.id.tvRegister)
        val auth = FirebaseAuth.getInstance()
        val firestore = FirebaseFirestore.getInstance()

        btnLogin.setOnClickListener {
            val email = etEMail.text.toString()
            val pass = etPass.text.toString()

            if (email.isEmpty() || pass.isEmpty()) {
                Toast.makeText(this, "Los campos email y contraseña no pueden estar vacíos",
                    Toast.LENGTH_SHORT).show()
            } else {
                auth.signInWithEmailAndPassword(email, pass)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            val userId = auth.currentUser?.uid
                            if (userId != null) {
                                val userDocRef = firestore.collection("users").document(userId)
                                userDocRef.get().addOnSuccessListener { document ->
                                    if (document != null && document.exists()) {
                                        val userType = document.getString("typeuser") ?: "user"
                                        val name = document.getString("name")
                                        val lastname = document.getString("lastname")


                                        val firstname = name?.split(" ")?.get(0)?.capitalize()
                                        val oneLetter = lastname?.get(0)?.uppercaseChar()
                                        val result = "$firstname $oneLetter."
                                        Log.d("USERNAME", result)

                                        Snackbar.make(
                                            findViewById(android.R.id.content),
                                            "Bienvenido",
                                            Snackbar.LENGTH_LONG
                                        ).show()

                                            val intent = Intent(this, MainActivity::class.java).apply {
                                            putExtra("USER_TYPE", userType)
                                            putExtra("USER_NAME", result)
                                        }
                                        startActivity(intent)
                                    } else {
                                        Snackbar.make(
                                            findViewById(android.R.id.content),
                                            "No se encontró el tipo de usuario",
                                            Snackbar.LENGTH_LONG
                                        ).show()
                                    }
                                }.addOnFailureListener {
                                    Snackbar.make(
                                        findViewById(android.R.id.content),
                                        "Error al obtener el tipo de usuario",
                                        Snackbar.LENGTH_LONG
                                    ).show()
                                }
                            } else {
                                Snackbar.make(
                                    findViewById(android.R.id.content),
                                    "Error al obtener el ID de usuario",
                                    Snackbar.LENGTH_LONG
                                ).show()
                            }
                        } else {
                            Snackbar.make(
                                findViewById(android.R.id.content),
                                "Credenciales inválidas",
                                Snackbar.LENGTH_LONG
                            ).show()
                        }
                    }
            }
        }

        tvRegister.setOnClickListener {
            // Registro de usuarios o empresas
            val intent = Intent(this, RegistroUsuario::class.java)
            startActivity(intent)
        }
    }
}
