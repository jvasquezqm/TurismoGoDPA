package com.example.turismogodpa.ui.company.publicacion

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.turismogodpa.R
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.*

class UpdatePubEmpActivity : AppCompatActivity() {
    private lateinit var spTipoPubUpd: Spinner
    private lateinit var etTituloPubUpd: EditText
    private lateinit var etDescPubUpd: EditText
    private lateinit var etLugarPubUpd: EditText
    private lateinit var etFechaPubUpd: EditText
    private lateinit var etHoraPubUpd: EditText
    private lateinit var etPrecioPubUpd: EditText
    private lateinit var etImagePubUpd: EditText
    private lateinit var btFormPubUpd: Button
    private var selectedDate: Calendar = Calendar.getInstance()
    private lateinit var documentId: String
    private val dateFormat = SimpleDateFormat("yyyy/MM/dd", Locale.getDefault())
    private val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_update_pub_emp)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        spTipoPubUpd = findViewById(R.id.spTipoPubUpd)
        etTituloPubUpd = findViewById(R.id.etTituloPubUpd)
        etDescPubUpd = findViewById(R.id.etDescPubUpd)
        etLugarPubUpd = findViewById(R.id.etLugarPubUpd)
        etFechaPubUpd = findViewById(R.id.etFechaPubUpd)
        etHoraPubUpd = findViewById(R.id.etHoraPubUpd)
        etPrecioPubUpd = findViewById(R.id.etPrecioPubUpd)
        etImagePubUpd = findViewById(R.id.etImagePubUpd)
        btFormPubUpd = findViewById(R.id.btFormPubUpd)

        // Set the time zone to the local time zone (e.g., Peru)
        selectedDate.timeZone = TimeZone.getTimeZone("America/Lima")

        // Get the document ID from the Intent
        documentId = intent.getStringExtra("DOCUMENT_ID") ?: ""

        Log.d("UpdatePubEmpActivity", "Document ID: $documentId")

        loadSpinnerData()
        loadActivityData()

        etFechaPubUpd.setOnClickListener {
            Log.d("UpdatePubEmpActivity", "Fecha EditText clicked")
            showDatePickerDialog()
        }

        etHoraPubUpd.setOnClickListener {
            Log.d("UpdatePubEmpActivity", "Hora EditText clicked")
            showTimePickerDialog()
        }

        btFormPubUpd.setOnClickListener {
            updateActivity()
        }
    }

    private fun loadSpinnerData() {
        val db = FirebaseFirestore.getInstance()

        db.collection("activitiestype").get()
            .addOnSuccessListener { documents ->
                val activities = ArrayList<String>()
                for (document in documents) {
                    document.getString("type")?.let { activities.add(it) }
                }
                val typeAddAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, activities)
                typeAddAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spTipoPubUpd.adapter = typeAddAdapter
                Log.d("UpdatePubEmpActivity", "Spinner data loaded successfully")
            }
            .addOnFailureListener { exception ->
                Log.e("UpdatePubEmpActivity", "Error al cargar tipos de actividades: ${exception.message}")
                Toast.makeText(this, "Error al cargar tipos de actividades: ${exception.message}", Toast.LENGTH_SHORT).show()
            }
    }

    private fun loadActivityData() {
        val db = FirebaseFirestore.getInstance()

        db.collection("activities").document(documentId).get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    etTituloPubUpd.setText(document.getString("titulo"))
                    etDescPubUpd.setText(document.getString("description"))
                    etLugarPubUpd.setText(document.getString("lugar"))

                    val timestamp = document.getTimestamp("time")
                    val date = timestamp?.toDate()
                    if (date != null) {
                        etFechaPubUpd.setText(dateFormat.format(date))
                        etHoraPubUpd.setText(timeFormat.format(date))
                    }

                    etPrecioPubUpd.setText(document.getDouble("price").toString())
                    etImagePubUpd.setText(document.getString("image"))

                    val type = document.getString("type")
                    val typeAddAdapter = spTipoPubUpd.adapter as? ArrayAdapter<String>
                    if (typeAddAdapter != null) {
                        val position = typeAddAdapter.getPosition(type)
                        if (position >= 0) {
                            spTipoPubUpd.setSelection(position)
                        } else {
                            Log.e("UpdatePubEmpActivity", "Type not found in adapter: $type")
                        }
                    } else {
                        Log.e("UpdatePubEmpActivity", "Adapter is null or not castable to ArrayAdapter<String>")
                    }

                    Log.d("UpdatePubEmpActivity", "Activity data loaded successfully")
                } else {
                    Log.e("UpdatePubEmpActivity", "Document is null")
                }
            }
            .addOnFailureListener { exception ->
                Log.e("UpdatePubEmpActivity", "Error al cargar datos: ${exception.message}")
                Toast.makeText(this, "Error al cargar datos: ${exception.message}", Toast.LENGTH_SHORT).show()
            }
    }

    private fun showDatePickerDialog() {
        val currentDate = Calendar.getInstance()
        DatePickerDialog(
            this,
            { _, year, month, dayOfMonth ->
                selectedDate.set(year, month, dayOfMonth)
                etFechaPubUpd.setText(dateFormat.format(selectedDate.time))
                Log.d("UpdatePubEmpActivity", "Selected date: ${dateFormat.format(selectedDate.time)}")
            },
            currentDate.get(Calendar.YEAR),
            currentDate.get(Calendar.MONTH),
            currentDate.get(Calendar.DAY_OF_MONTH)
        ).show()
    }

    private fun showTimePickerDialog() {
        val currentTime = Calendar.getInstance()
        TimePickerDialog(
            this,
            { _, hourOfDay, minute ->
                selectedDate.set(Calendar.HOUR_OF_DAY, hourOfDay)
                selectedDate.set(Calendar.MINUTE, minute)
                etHoraPubUpd.setText(timeFormat.format(selectedDate.time))
                Log.d("UpdatePubEmpActivity", "Selected time: ${timeFormat.format(selectedDate.time)}")
            },
            currentTime.get(Calendar.HOUR_OF_DAY),
            currentTime.get(Calendar.MINUTE),
            true
        ).show()
    }

    private fun updateActivity() {
        val titulo = etTituloPubUpd.text.toString()
        val description = etDescPubUpd.text.toString()
        val lugar = etLugarPubUpd.text.toString()
        val priceText = etPrecioPubUpd.text.toString()
        val image = etImagePubUpd.text.toString()
        val type = spTipoPubUpd.selectedItem.toString()

        if (titulo.isEmpty() || description.isEmpty() || lugar.isEmpty() || etFechaPubUpd.text.isEmpty() || etHoraPubUpd.text.isEmpty() || priceText.isEmpty() || image.isEmpty()) {
            Toast.makeText(this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show()
            return
        }

        val price = priceText.toDoubleOrNull()
        if (price == null) {
            Toast.makeText(this, "El precio debe ser un número válido", Toast.LENGTH_SHORT).show()
            return
        }

        val db = FirebaseFirestore.getInstance()

        val timeInMillis = selectedDate.timeInMillis
        val timestamp = Timestamp(Date(timeInMillis))

        val activityData = mapOf(
            "titulo" to titulo,
            "description" to description,
            "lugar" to lugar,
            "price" to price,
            "image" to image,
            "type" to type,
            "time" to timestamp
        )

        db.collection("activities").document(documentId).update(activityData)
            .addOnSuccessListener {
                Log.d("UpdatePubEmpActivity", "Activity updated successfully")
                Toast.makeText(this, "Actividad actualizada correctamente", Toast.LENGTH_SHORT).show()
                finish()
            }
            .addOnFailureListener { exception ->
                Log.e("UpdatePubEmpActivity", "Error al actualizar actividad: ${exception.message}")
                Toast.makeText(this, "Error al actualizar actividad: ${exception.message}", Toast.LENGTH_SHORT).show()
            }
    }

    private fun enableEdgeToEdge() {
        // Enable edge-to-edge mode for a better appearance on devices with cutouts
        window.decorView.systemUiVisibility = (
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                )
     }
}