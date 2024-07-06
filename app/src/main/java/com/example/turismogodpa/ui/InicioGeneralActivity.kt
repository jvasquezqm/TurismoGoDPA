package com.example.turismogodpa.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.turismogodpa.R
import com.example.turismogodpa.adapter.ActividadHomeAdapter
import com.example.turismogodpa.data.model.ActividadesHomeModel
import com.example.turismogodpa.data.model.UserProfile
import com.example.turismogodpa.ui.autentication.LoginActivity
import com.example.turismogodpa.ui.autentication.dataStore
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext


class InicioGeneralActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_inicio_general)
        supportActionBar?.hide()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val btBuscar: Button = findViewById(R.id.btBuscar)
        var anuncioToast = Toast.makeText(this, "Por favor Inicie Sesion", Toast.LENGTH_LONG)

        lifecycleScope.launch(Dispatchers.IO) {

            getUserProfile().collect {
                withContext(Dispatchers.Main) {

                    Log.i("User Profile", "${it.name} - ${it.email}")
                    //etName.setText(it.name)
                    //etEmail.setText(it.email)
                }

            }
        }


        val firestore = FirebaseFirestore.getInstance()
        //Traer datos de la base de datos
        firestore.collection("bookings").document("7eEVWWr2ffQF9CTXEwRw")
            .get()
            .addOnSuccessListener {
            document ->
            if(document != null) {
               val reference = document.getDocumentReference("idUser")
                if(reference!=null)
                {
                    reference.get().addOnSuccessListener {
                        userDoc ->
                        if(userDoc!=null)
                        {
                            Log.i("User Document", "Usuario: ${userDoc.data}")
                           val maild=userDoc.getString("email")
                            println(maild)
                        }
                        else
                        {
                            Log.i("User Document", "No se encontro el usuario")
                        }
                    }
                }

            }
        }

        //Registrar
        /*
        val collectionDB=firestore.collection("bookings")
        val newBookingData = hashMapOf(
            "name" to "Nombre del nuevo booking",
            "iduser" to FirebaseFirestore.getInstance().document("users/77LhqWOUhXgB1FYz9tUnDoupCO73")  // Reemplaza "userId" con el ID del usuario correspondiente
        )
        collectionDB.add(newBookingData)
            .addOnSuccessListener { documentReference ->
                Log.i("Booking", "DocumentSnapshot added with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Log.e("Booking", "Error adding document", e)
            }
        */




        btBuscar.setOnClickListener {
            anuncioToast.show()
            val intent = Intent(this@InicioGeneralActivity, LoginActivity::class.java)
            startActivity(intent)
        }

/*
        val ActividadHomeAdapter = ActividadHomeAdapter(ListActividades(), object : ActividadHomeAdapter.OnItemClickListener {
            override fun onItemClick(actividad: ActividadesHomeModel) {
                anuncioToast.show()
                val intent = Intent(this@InicioGeneralActivity, LoginActivity::class.java)
                startActivity(intent)
            }
        })
        val recyclerView: RecyclerView = findViewById(R.id.rvActividadesH)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = ActividadHomeAdapter

        */

        val db = FirebaseFirestore.getInstance()
        var lstActividadesH: List<ActividadesHomeModel>
        val rvActividadesH: RecyclerView = findViewById(R.id.rvActividadesH)

        db.collection("activitieshome")
            .addSnapshotListener{ snap, errror ->
                if (errror != null){
                    Log.e("ERROR-FIREBASE", "Ocurrio error: ${errror.message}")
                    return@addSnapshotListener
                }
                lstActividadesH = snap!!.documents.map{document ->
                    ActividadesHomeModel(
                        document.id,
                        document["image"].toString(),
                        document["name"].toString(),
                        document["description"].toString(),
                        document["date"].toString(),
                        document["type"].toString(),
                        document["price"].toString(),
                        document["company"].toString()
                    )}
                rvActividadesH.adapter = ActividadHomeAdapter(lstActividadesH, object : ActividadHomeAdapter.OnItemClickListener {
                    override fun onItemClick(actividad: ActividadesHomeModel) {
                        anuncioToast.show()
                        val intent = Intent(this@InicioGeneralActivity, LoginActivity::class.java)
                        startActivity(intent)
                    }
                })
                rvActividadesH.layoutManager = LinearLayoutManager(this)
            }
        //Añadir linea de división de los elementos de la lista del recycler view
        var deco = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        deco.setDrawable(getDrawable(R.drawable.divider) ?: resources.getDrawable(R.drawable.divider, theme))
        rvActividadesH.addItemDecoration(deco)

    }

/*
    private fun ListActividades(): List<ActividadesHomeModel> {
        val lstActividades = ArrayList<ActividadesHomeModel>()
        lstActividades.add(
            ActividadesHomeModel(
                "//R.drawable.img_actvidad01",
                "Actividad 1",
                " Deporte de riesgo que consiste en tirarse al vacío desde un puente u " +
                        "otro lugar elevado, sujetándose con una cuerda elástica",
                "01/01/2021",
                "Tipo de actividad 1"))


        lstActividades.add(
            ActividadesHomeModel(
                "R.drawable.img_actividad02",
                "Actividad 2",
                "Machu Picchu es una de las 7 maravillas del mundo más visitadas por los " +
                        "turistas, posee hermosas construcciones a base de piedras, que fueron " +
                        "talladas con mucha precisión y detalle, es la obra más importante para los " +
                        "incas por haber sido construida en una montaña agreste e inaccesible, " +
                        "dividida en dos grandes sectores, urbano y agrícola separados por una gran " +
                        "muralla que desciende por la ladera del cerro hasta llegar a las orillas " +
                        "del rio Vilcanota",
                "02/01/2021",
                "Tipo de actividad 2"))

        lstActividades.add(
            ActividadesHomeModel(
                "R.drawable.img_actividad03",
                "Actividad 3",
                "El Parque Nacional de Huascarán es un área natural protegida del Perú, " +
                        "ubicada en la Cordillera Blanca, en la región de Áncash. Fue creado el 1 de " +
                        "julio de 1975 y tiene una extensión de 340 000 hectáreas. El parque " +
                        "comprende una gran parte de la Cordillera Blanca, la cadena montañosa más " +
                        "alta de los Andes peruanos y de América tropical. En 1985 fue declarado " +
                        "Patrimonio de la Humanidad por la Unesco",
                "03/01/2021",
                "Tipo de actividad 3"))
        return lstActividades

    }
*/
    private fun getUserProfile() = dataStore.data.map{preferences ->
        UserProfile(
            name = preferences[stringPreferencesKey("name")].orEmpty(),
            email = preferences[stringPreferencesKey("email")].orEmpty(),
            userId = preferences[stringPreferencesKey("userId")].orEmpty()

        )

    }


}