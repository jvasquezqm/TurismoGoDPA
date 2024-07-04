package com.example.turismogodpa.ui.company.cuenta

import RucResponse
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.turismogodpa.R
import com.example.turismogodpa.data.model.CompanyModel
import com.example.turismogodpa.service.ApiService
import com.example.turismogodpa.ui.InicioGeneralActivity
import com.example.turismogodpa.ui.autentication.LoginActivity
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
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
        supportActionBar
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        etRuc = findViewById(R.id.etRuc)
        etRazonSocial = findViewById(R.id.etRazonSocial)
        etDireccionComp = findViewById(R.id.etDireccionComp)
        val db = FirebaseFirestore.getInstance()
        val auth = FirebaseAuth.getInstance()
        val etPhone: EditText = findViewById(R.id.etPhone)
        val etNameContacto: EditText = findViewById(R.id.etNameContacto)
        val etCorreoComp: EditText = findViewById(R.id.etCorreoComp)
        val etPassComp1: EditText = findViewById(R.id.etPassComp1)
        val etPassComp2: EditText = findViewById(R.id.etPassComp2)
        val btRegistroEmpresa: Button = findViewById(R.id.btRegistroEmpresa)


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
                if (s?.length == 11) {
                    validateRuc(s.toString())
                } else {
                    etRazonSocial.text.clear()
                    etDireccionComp.text.clear()
                    etRazonSocial.isEnabled = false
                    etDireccionComp.isEnabled = false


                }
            }
        })


        btRegistroEmpresa.setOnClickListener {
            val ruc = etRuc.text.toString()
            val razonSocial = etRazonSocial.text.toString()
            val direccion = etDireccionComp.text.toString()
            val pass1 = etPassComp1.text.toString()
            val pass2 = etPassComp2.text.toString()
            val phone = etPhone.text.toString()
            val name = etNameContacto.text.toString()
            val email = etCorreoComp.text.toString()


            val typeuser ="company"
            if (isCompanyActive) {

                if (ruc.isEmpty() || razonSocial.isEmpty() || direccion.isEmpty() || pass1.isEmpty() || pass2.isEmpty() || phone.isEmpty() || name.isEmpty() || email.isEmpty()){
                    Toast.makeText(this, "los campos no pueden estar vacíos",
                        Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                if (pass1 != pass2) {
                    Toast.makeText(this, "Las contraseñas no coinciden",
                        Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
                if (!isValidEmail(email)) {
                    Toast.makeText(this, "El correo electrónico no tiene un formato válido", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }


                auth.createUserWithEmailAndPassword(email, pass1)
                    .addOnCompleteListener(this) { task ->

                        if (task.isSuccessful) {
                            val user: FirebaseUser? = auth.currentUser
                            val uid = user?.uid

                            val companyModel =
                                CompanyModel(ruc,razonSocial,name,phone,email,direccion,typeuser)

                            db.collection("users").document(uid!!)
                                .set(companyModel)
                                .addOnSuccessListener {
                                    Toast.makeText(this,"Empresa registrada correctamente",
                                        Toast.LENGTH_LONG).show()
                                    CleanCampos(etRuc,etRazonSocial,etDireccionComp,etPassComp1,etPassComp2,etPhone,etNameContacto,etCorreoComp)

                                    val intent = Intent(this@RegistroEmpresa, LoginActivity::class.java)
                                    startActivity(intent)
                                }
                                .addOnFailureListener { error ->
                                    Toast.makeText(this,"Error al registrar empresa",
                                        Toast.LENGTH_LONG).show()

                                }
                        }

                    }

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
                        Toast.makeText(this@RegistroEmpresa, "La empresa no está activa", Toast.LENGTH_SHORT).show()
                        isCompanyActive = false
                    }
                } else {
                    Toast.makeText(this@RegistroEmpresa, "Error validando RUC", Toast.LENGTH_SHORT).show()
                    isCompanyActive = false
                }
            }

            override fun onFailure(call: Call<RucResponse?>, t: Throwable) {
                Toast.makeText(this@RegistroEmpresa, "Error de red", Toast.LENGTH_SHORT).show()
                isCompanyActive = false
            }
        })
    }

    private fun redirectToInicioGeneral() {
        val intent = Intent(this, InicioGeneralActivity::class.java)
        startActivity(intent)
        finish() // Optional: Call finish() if you want to close the current activity
    }
    fun CleanCampos(
        etRuc:  EditText, etRazonSocial: EditText, etDireccionComp: EditText, etPassComp1: EditText, etPassComp2: EditText,
        etPhone: EditText,
        etNameContacto: EditText,
        etCorreoComp: EditText
    ){
        etRuc.text.clear()
        etRazonSocial.text.clear()
        etDireccionComp.text.clear()
        etPassComp1.text.clear()
        etPassComp2.text.clear()
        etPhone.text.clear()
        etNameContacto.text.clear()
        etCorreoComp.text.clear()
    }
    fun isValidEmail(target: CharSequence?): Boolean {
        return if (target == null) false else android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches()
    }


}
