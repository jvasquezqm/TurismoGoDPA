package com.example.turismogodpa.ui.review

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.lifecycleScope
import com.example.turismogodpa.R
import com.example.turismogodpa.data.model.UserProfile
import com.example.turismogodpa.ui.autentication.dataStore
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Locale

class AddReviewActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_add_review)
        supportActionBar?.hide()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val ivVolver: ImageView = findViewById(R.id.ivVolverReview)
        val tvVolver: TextView = findViewById(R.id.tvVolverReview)
        val etTittleReview: TextView = findViewById(R.id.etTittleReview)
        val etDescriptionReview: TextView = findViewById(R.id.etComentario)
        val btnEnviarReview: TextView = findViewById(R.id.btnPublicarReview)


        btnEnviarReview.setOnClickListener {
            if (etTittleReview.text.toString().isNotEmpty() && etDescriptionReview.text.toString().isNotEmpty()) {
                lifecycleScope.launch(Dispatchers.IO) {
                    getUserProfile().collect {
                        withContext(Dispatchers.Main) {
                            Log.d("AddReviewActivity", "ID USUARIO EN ACTIVIDAD REVIEW: ${it.userId}")
                            //Ajustar fecha
                            val sdf = SimpleDateFormat("dd/MM/yyyy hh:mm:ss", Locale.getDefault())
                            val currentDateanTime = sdf.format(System.currentTimeMillis())
                            val currentTimestamp = com.google.firebase.Timestamp.now()
                            //Recibir idActividad
                            val idAct: String = intent.extras?.getString("idactivity").orEmpty()

                            //Log.e("AddReviewActivity", "ID ACTIVIDAD EN ACTIVIDAD REVIEW2: $idAct")

                            // Guardar en Firestore
                            val db = FirebaseFirestore.getInstance()
                            val review2 = hashMapOf(
                                "titulo" to etTittleReview.text.toString(),
                                "comment" to etDescriptionReview.text.toString(),
                                "iduser" to it.userId,
                                "time" to currentTimestamp,
                                "idactivity" to idAct
                            )

                            db.collection("reviews2")
                                .add(review2)
                                .addOnSuccessListener { documentReference ->
                                    Log.d("AddReviewActivity", "DocumentSnapshot added with ID: ${documentReference.id}")
                                    Toast.makeText(this@AddReviewActivity, "Review publicado", Toast.LENGTH_SHORT).show()
                                    onBackPressed()
                                }
                                .addOnFailureListener { e ->
                                    Log.w("AddReviewActivity", "Error adding document", e)
                                    Toast.makeText(
                                        this@AddReviewActivity,
                                        "Error al publicar review",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                        }
                    }



                }
            } else {
                Toast.makeText(this, "Por favor, llene todos los campos", Toast.LENGTH_SHORT).show()
            }
        }

        ivVolver.setOnClickListener {
            onBackPressed()
        }
        tvVolver.setOnClickListener {
            onBackPressed()
        }

    }
    private fun getUserProfile() = dataStore.data.map{preferences ->
        UserProfile(
            name = preferences[stringPreferencesKey("name")].orEmpty(),
            email = preferences[stringPreferencesKey("email")].orEmpty(),
            userId = preferences[stringPreferencesKey("userId")].orEmpty()
        )

    }
}