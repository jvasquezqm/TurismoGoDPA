package com.example.turismogodpa.ui.autentication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.turismogodpa.MainActivity
import com.example.turismogodpa.R
import com.example.turismogodpa.ui.InicioGeneralActivity


class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val typeUser: CheckBox = findViewById(R.id.cbTypeUser)
        val etEMail: EditText = findViewById(R.id.etEmail)
        val etPass: EditText = findViewById(R.id.etPassword)
        val btnLogin: Button = findViewById(R.id.btnLogin)
        val tvRegister: TextView = findViewById(R.id.tvRegister)

        btnLogin.setOnClickListener {
            val email = etEMail.text.toString()
            val pass = etPass.text.toString()
            val userType = if (typeUser.isChecked) "company" else "user"

            if (email == "admin" && pass == "admin") {
                val intent = Intent(this, MainActivity::class.java).apply {
                    putExtra("USER_TYPE", userType)
                }
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "Credenciales inválidas", Toast.LENGTH_SHORT).show()
            }
        }
        tvRegister.setOnClickListener {
            if(typeUser.isChecked){
                //val intent = Intent(this,RegistroEmpresa::class.java)
                val intent = Intent(this,InicioGeneralActivity::class.java)
                startActivity(intent)
            }else{
                //val intent = Intent(this,RegistroERegistroUsuario::class.java)
                val intent = Intent(this, InicioGeneralActivity::class.java)
                startActivity(intent)
            }
        }
    }
}
