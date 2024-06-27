package com.example.turismogodpa.ui.company.cuenta

import RucResponse
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.turismogodpa.R
import com.example.turismogodpa.service.ApiService
import com.example.turismogodpa.ui.InicioGeneralActivity
import com.google.android.material.snackbar.Snackbar
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RegistroEmpresa : AppCompatActivity() {

    private lateinit var etRuc: EditText
    private lateinit var etRazonSocial: EditText
    private lateinit var etDireccionComp: EditText
    private lateinit var apiService: ApiService
    private var isCompanyActive = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_registro_empresa)
        supportActionBar?.hide()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        etRuc = findViewById(R.id.etRuc)
        etRazonSocial = findViewById(R.id.etRazonSocial)
        etDireccionComp = findViewById(R.id.etDireccionComp)

        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.apis.net.pe/v2/")
            .client(OkHttpClient.Builder().build())
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        apiService = retrofit.create(ApiService::class.java)

        etRuc.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                if (s?.length == 11) {  // Assuming RUC has 11 digits
                    validateRuc(s.toString())
                }
            }
        })

        val btRegistroEmpresa: Button = findViewById(R.id.btRegistroEmpresa)
        btRegistroEmpresa.setOnClickListener {
            if (isCompanyActive) {
                // Proceed with registration
                val rootView: View = findViewById(android.R.id.content)
                Snackbar.make(rootView, "Bienvenido a Turismo Go App", Snackbar.LENGTH_LONG).show()
                // Add your registration logic here
            } else {
                Snackbar.make(findViewById(android.R.id.content), "La empresa no está activa", Snackbar.LENGTH_SHORT).show()
                redirectToInicioGeneral()
            }
        }
    }

    private fun validateRuc(ruc: String) {
        val token = "Bearer apis-token-9207.mnOpxszzgK8ONFQo88sQUTzId03woOep"
        val call = apiService.getRucInfo(ruc, token)
        call?.enqueue(object : Callback<RucResponse?> {
            override fun onResponse(call: Call<RucResponse?>, response: Response<RucResponse?>) {
                if (response.isSuccessful) {
                    val rucResponse = response.body()
                    if (rucResponse != null && rucResponse.estado.equals("ACTIVO", ignoreCase = true)) {
                        etRazonSocial.setText(rucResponse.razonSocial)
                        etDireccionComp.setText(rucResponse.direccion)
                        isCompanyActive = true
                    } else {
                        Snackbar.make(findViewById(android.R.id.content), "RUC no activo", Snackbar.LENGTH_SHORT).show()
                        isCompanyActive = false
                    }
                } else {
                    Snackbar.make(findViewById(android.R.id.content), "Error validando RUC", Snackbar.LENGTH_SHORT).show()
                    isCompanyActive = false
                }
            }

            override fun onFailure(call: Call<RucResponse?>, t: Throwable) {
                Snackbar.make(findViewById(android.R.id.content), "Error de red", Snackbar.LENGTH_SHORT).show()
                isCompanyActive = false
            }
        })
    }

    private fun redirectToInicioGeneral() {
        val intent = Intent(this, InicioGeneralActivity::class.java)
        startActivity(intent)
        finish() // Optional: Call finish() if you want to close the current activity
    }
}
