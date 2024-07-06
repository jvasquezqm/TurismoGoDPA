package com.example.turismogodpa.ui.company.cuenta

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.turismogodpa.R
import com.example.turismogodpa.R.id.btActualizarPerfilEmpresa
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FirebaseFirestore

class PerfilEmpresa : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_perfil_empresa)
        supportActionBar?.hide()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        val etRucPerfilEmpresa: EditText = findViewById(R.id.etRucPerfilEmpresa)
        val etRazonSocialPerfilEmpresa: EditText = findViewById(R.id.etRazonSocialPerfilEmpresa)
        val etNombreContactoPerfilEmpresa: EditText = findViewById(R.id.etNombreContactoPerfilEmpresa)
        val etCorreoContactoPerfilEmpresa: EditText = findViewById(R.id.etCorreoContactoPerfilEmpresa)
        val etDireccionPerfilEmpresa: EditText = findViewById(R.id.etDireccionPerfilEmpresa)
        val etTelefonoPerfilEmpresa: EditText = findViewById(R.id.etTelefonoPerfilEmpresa)
        val etEstadoPerfilEmpresa: EditText = findViewById(R.id.etEstadoPerfilEmpresa)
        val btActualizarPerfilEmpresa : Button = findViewById(R.id.btActualizarPerfilEmpresa)
        val btDesactivarPerfilEmpresa : Button = findViewById(R.id.DesactivarPerfilEmpresa)
        val db = FirebaseFirestore.getInstance()
        val currentUser = FirebaseAuth.getInstance().currentUser
        val userId = currentUser?.uid


        val docRef = db.collection("users").document(userId!!)
        docRef.get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    // Aquí document contiene los datos del usuario
                    // Puedes acceder a los campos y actualizarlos según sea necesario
                    etRucPerfilEmpresa.setText(document.getString("ruc"))
                    etRazonSocialPerfilEmpresa.setText(document.getString("razonSocial"))
                    etNombreContactoPerfilEmpresa.setText(document.getString("name"))
                    etCorreoContactoPerfilEmpresa.setText(document.getString("email"))
                    etDireccionPerfilEmpresa.setText(document.getString("address"))
                    etTelefonoPerfilEmpresa.setText(document.getString("phone"))
                    etEstadoPerfilEmpresa.setText(document.getString("estado"))

                    // Implementa la lógica de actualización al presionar el botón
                    btActualizarPerfilEmpresa.setOnClickListener {
                        // Aquí puedes actualizar los campos en Firestore
                        val newData = hashMapOf(
                            "ruc" to etRucPerfilEmpresa.text.toString(),
                            "razonSocial" to etRazonSocialPerfilEmpresa.text.toString(),
                            "name" to etNombreContactoPerfilEmpresa.text.toString(),
                            "email" to etCorreoContactoPerfilEmpresa.text.toString(),
                            "address" to etDireccionPerfilEmpresa.text.toString(),
                            "phone" to etTelefonoPerfilEmpresa.text.toString(),
                            "estado" to etEstadoPerfilEmpresa.text.toString()
                        )

                        // Actualiza los datos en Firestore
                        docRef.update(newData as Map<String, Any>)
                            .addOnSuccessListener {
                                Log.d("Firebase", "Document successfully updated!")
                                // Aquí puedes manejar la confirmación de la actualización
                            }
                            .addOnFailureListener { e ->
                                Log.w("Firebase", "Error updating document", e)
                                // Aquí puedes manejar los errores de la actualización
                            }
                    }

                    // Implementa la lógica de desactivación al presionar el botón
                    btDesactivarPerfilEmpresa.setOnClickListener {
                        // Aquí puedes desactivar la cuenta del usuario o realizar otras acciones necesarias
                    }
                } else {
                    Log.d("Firebase", "No such document")
                }
            }
            .addOnFailureListener { exception ->
                Log.d("Firebase", "get failed with ", exception)
            }





    }
}