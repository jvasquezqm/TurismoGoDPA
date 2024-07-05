package com.example.turismogodpa.ui.user.cuenta

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.turismogodpa.R
import com.example.turismogodpa.data.model.UserModel
import com.example.turismogodpa.ui.autentication.LoginActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore

class RegistroUsuario : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_registro_usuario)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val db = FirebaseFirestore.getInstance()
        val auth = FirebaseAuth.getInstance()
        val etName: EditText = findViewById(R.id.etName)
        val etLastnameComp: EditText = findViewById(R.id.etLastname)
        val etDNIComp: EditText = findViewById(R.id.etDNI)
        val etEmailComp: EditText = findViewById(R.id.etEmaiUs)
        val etPhoneEmp: EditText = findViewById(R.id.etPhoneUs)
        val etFechNac: EditText = findViewById(R.id.etFechNac)
        val etPassEmp1: EditText = findViewById(R.id.etPass1)
        val etPassEmp2: EditText = findViewById(R.id.etPass2)
        val btRegistroUsuario: Button = findViewById(R.id.btRegistroUsuario)

        btRegistroUsuario.setOnClickListener {
            val name = etName.text.toString()
            val lastname = etLastnameComp.text.toString()
            val dni = etDNIComp.text.toString()
            val email = etEmailComp.text.toString()
            val phone = etPhoneEmp.text.toString()
            val fechaNac = etFechNac.text.toString()
            val pass1 = etPassEmp1.text.toString()
            val pass2 = etPassEmp2.text.toString()
            val typeuser ="user"

//VALIDACION DE CAMPOS VACIOSs
            if (name.isEmpty() || lastname.isEmpty() || dni.isEmpty() || email.isEmpty() || phone.isEmpty() || fechaNac.isEmpty() || pass1.isEmpty() || pass2.isEmpty()) {
                Toast.makeText(this, "los campos no pueden estar vacíos",
                    Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (pass1 != pass2) {
                Toast.makeText(this, "Las contraseñas no coinciden",
                    Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            auth.createUserWithEmailAndPassword(email, pass1)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        val user: FirebaseUser? = auth.currentUser
                        val uid = user?.uid
                        val userModel =
                            UserModel(name, lastname, email, dni, phone, fechaNac, typeuser)

                        db.collection("users").document(uid!!)
                            .set(userModel)
                            .addOnSuccessListener {
                                Toast.makeText(this,"Usuario registrado correctamente",
                                    Toast.LENGTH_LONG).show()
                                CleanCampos(etName,etLastnameComp,etDNIComp,etEmailComp,etPhoneEmp,etFechNac,etPassEmp1,etPassEmp2)

                                val intent = Intent(this@RegistroUsuario, LoginActivity::class.java)
                                startActivity(intent)
                            }
                            .addOnFailureListener { error ->
                                Toast.makeText(this,"Error al registrar usuario",
                                    Toast.LENGTH_LONG).show()

                            }
                    }

                }

        }
    }
    fun CleanCampos(etNameComp:  EditText, etLastnameComp: EditText, etDNIComp: EditText,etEmailComp: EditText,
                    etPhoneEmp: EditText, etFechNac: EditText, etPassEmp1: EditText, etPassEmp2: EditText){
        etNameComp.text.clear()
        etLastnameComp.text.clear()
        etDNIComp.text.clear()
        etEmailComp.text.clear()
        etPhoneEmp.text.clear()
        etFechNac.text.clear()
        etPassEmp1.text.clear()
        etPassEmp2.text.clear()
    }
}