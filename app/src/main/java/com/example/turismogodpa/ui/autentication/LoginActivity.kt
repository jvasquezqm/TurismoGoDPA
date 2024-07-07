package com.example.turismogodpa.ui.autentication

import android.content.Context
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
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.lifecycleScope
import com.example.turismogodpa.MainActivity
import com.example.turismogodpa.R
import com.example.turismogodpa.ui.company.cuenta.RegistroEmpresa
import com.example.turismogodpa.ui.user.cuenta.RegistroUsuario
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

val Context.dataStore by preferencesDataStore(name = "USER PREFERENCES_EMAIL_NAME")
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
        val tvRegisterEmp: TextView = findViewById(R.id.tvRegisterEmp)
        val auth = FirebaseAuth.getInstance()
        val firestore = FirebaseFirestore.getInstance()

        btnLogin.setOnClickListener {
            val email = etEMail.text.toString()
            val pass = etPass.text.toString()
            var result = ""


            if (email.isEmpty() || pass.isEmpty()) {
                Toast.makeText(this, "Los campos email y contraseña no pueden estar vacíos",
                    Toast.LENGTH_SHORT).show()
            }
            else {
                auth.signInWithEmailAndPassword(email, pass)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            val userId = auth.currentUser?.uid
                            if (userId != null) {
                                val userDocRef = firestore.collection("users").document(userId)
                                userDocRef.get().addOnSuccessListener { document ->
                                    if (document != null && document.exists()) {
                                        val userType = document.getString("typeuser") ?: "user"
                                        val name = document.getString("name")?:document.getString("razonsocial")
                                        //val lastname = document.getString("lastname") ?: document.getString("razonsocial")

                                        val firstname = name?.split(" ")?.get(0)?.capitalize()
                                        //val oneLetter = lastname?.get(0)?.uppercaseChar()
                                        //var result = "$firstname $oneLetter."
                                         result = result.plus(firstname)

                                        Log.d("USERNAME", result)
                                        lifecycleScope.launch(Dispatchers.IO) {
                                            saveValues(email, result, userId)
                                        }


                                        Snackbar.make(
                                            findViewById(android.R.id.content),
                                            "Bienvenido",
                                            Snackbar.LENGTH_LONG
                                        ).show()

                                            val intent = Intent(this, MainActivity::class.java).apply {
                                            putExtra("USER_TYPE", userType)
                                        }
                                        startActivity(intent)
                                    }
                                    else {
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
                // Limpiar campos de Login
                etEMail.text.clear()
                etPass.text.clear()
            }
            Log.i("RESULTADOFINAL", result.plus(result))



        }


        tvRegister.setOnClickListener {
            // Registro de usuarios o empresas
            val intent = Intent(this, RegistroUsuario::class.java)
            startActivity(intent)
        }
        tvRegisterEmp.setOnClickListener {
            // Registro de usuarios o empresas
            val intent = Intent(this, RegistroEmpresa::class.java)
            startActivity(intent)
        }
    }
    private suspend fun saveValues(email: String, result: String, userId: String){
       dataStore.edit { preferences ->
            preferences[stringPreferencesKey("email")] = email
            preferences[stringPreferencesKey("name")] = result
            preferences[stringPreferencesKey("userId")] = userId
        }
    }
}
