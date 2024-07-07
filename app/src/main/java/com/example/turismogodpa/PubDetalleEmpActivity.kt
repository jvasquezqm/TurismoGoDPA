package com.example.turismogodpa

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.turismogodpa.fragments.ComentPubDiaFragment
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.Locale
import com.squareup.picasso.Picasso

class PubDetalleEmpActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_pub_detalle_emp)
        supportActionBar?.hide()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        // Obtener el UID de la actividad desde el intent
        val uid = intent.getStringExtra("UID")
        Log.d("PubDetalleEmpActivity", "Using fixed UID: $uid")

        // Verificar si el UID no es nulo ni vacío
        if (!uid.isNullOrEmpty()) {

            // Obtener el documento desde Firebase usando el UID fijo
            val db = FirebaseFirestore.getInstance()
            db.collection("activities").document(uid).get()
                .addOnSuccessListener { document ->
                    if (document != null) {
                        val imageUrl = document.getString("image")
                        val titulo = document.getString("titulo")
                        val time = document.getTimestamp("time")
                        val type = document.getString("type")
                        val state = document.getString("state")
                        val lugar = document.getString("lugar")
                        val description = document.getString("description")
                        val precio = document.getDouble("price")

                        // Formatear el timestamp a una fecha legible
                        val dateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
                        val formattedDate = time?.toDate()?.let { dateFormat.format(it) }

                        // Actualizar la UI con los datos obtenidos
                        findViewById<TextView>(R.id.tvActDetTitulo).text = titulo
                        findViewById<TextView>(R.id.tvActDetTime).text = formattedDate
                        findViewById<TextView>(R.id.tvActDetType).text = type
                        findViewById<TextView>(R.id.tvActDetLugar).text = lugar
                        findViewById<TextView>(R.id.tvActDetDescription).text = description
                        findViewById<TextView>(R.id.tvActDetPrecio).text = precio?.toString()

                        // Cargar la imagen usando Picasso
                        val imageView = findViewById<ImageView>(R.id.ivActDetImagen)
                        Picasso.get().load(imageUrl).into(imageView)

                        // Configurar el botón para mostrar usuarios inscritos
                        val imbUserPubDet: ImageButton = findViewById(R.id.imbUserPubDet)
                        imbUserPubDet.setOnClickListener {
                            val intent = Intent(this, PubHistUsuariosEmpActivity::class.java)
                            intent.putExtra("UID", uid) // Pasar el UID
                            startActivity(intent)
                        }

                        // Configurar el botón para mostrar comentarios
                        val imbComentPubDet: ImageButton = findViewById(R.id.imbComentPubDet)
                        imbComentPubDet.setOnClickListener {
                            val dialog = ComentPubDiaFragment.newInstance(uid) // Pasar el UID
                            dialog.show(supportFragmentManager, "CommentDialogFragment")
                        }



                    } else {
                        Log.e("PubDetalleEmpActivity", "No document found")
                    }
                }
                .addOnFailureListener { exception ->
                    Log.e("PubDetalleEmpActivity", "Error getting document: ", exception)
                }
        } else {
            Log.e("PubDetalleEmpActivity", "No UID provided or UID is empty")
        }
    }
}