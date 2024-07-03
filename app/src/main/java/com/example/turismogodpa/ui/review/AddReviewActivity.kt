package com.example.turismogodpa.ui.review

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.lifecycleScope
import com.example.turismogodpa.R
import com.example.turismogodpa.data.model.UserProfile
import com.example.turismogodpa.ui.autentication.dataStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

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

        /*lifecycleScope.launch(Dispatchers.IO) {
            getUserProfile().map { userProfile ->
                findViewById<TextView>(R.id.tvNombreUsuario).text = userProfile.name
                findViewById<TextView>(R.id.tvCorreoUsuario).text = userProfile.email
            }
        }*/
        //Obtener name y email del usuario a partir de getUserProfile
        lifecycleScope.launch(Dispatchers.IO) {
            getUserProfile().map { userProfile ->
                val nameD = userProfile.name
                val emailD = userProfile.email
                println(nameD)
                println(emailD)
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
            email = preferences[stringPreferencesKey("email")].orEmpty()
        )

    }
}