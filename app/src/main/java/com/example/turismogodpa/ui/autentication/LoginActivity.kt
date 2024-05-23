package com.example.turismogodpa.ui.autentication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.turismogodpa.MainActivity
import com.example.turismogodpa.R

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val etEMail: EditText = findViewById(R.id.etEmail)
        val etPass: EditText = findViewById(R.id.etPassword)
        val btnLogin: Button = findViewById(R.id.btnLogin)

        btnLogin.setOnClickListener {
            val email = etEMail.text.toString()
            val pass = etPass.text.toString()
            if (email == "admin" && pass == "admin") {
                val intent=(Intent(this, MainActivity::class.java))
                startActivity(intent)
            } else {
                Toast.makeText(this,
                    "Credenciales inválidas",
                    Toast.LENGTH_SHORT).show()
            }

        }
    }
}