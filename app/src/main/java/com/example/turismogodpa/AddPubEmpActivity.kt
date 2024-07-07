package com.example.turismogodpa

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.turismogodpa.data.model.PubAddModel
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.util.Log
import java.util.Calendar
import java.util.TimeZone

class AddPubEmpActivity : AppCompatActivity() {

    private lateinit var spTipoPubAdd: Spinner
    private lateinit var etTituloPubAdd: EditText
    private lateinit var etDescPubAdd: EditText
    private lateinit var etLugarPubAdd: EditText
    private lateinit var etFechaPubAdd: EditText
    private lateinit var etHoraPubAdd: EditText
    private lateinit var etPrecioPubAdd: EditText
    private lateinit var etImagePubAdd: EditText
    private lateinit var btFormPubAdd: Button
    private var selectedDate: Calendar = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_add_pub_emp)
        supportActionBar?.hide()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        spTipoPubAdd = findViewById(R.id.spTipoPubAdd)
        etTituloPubAdd = findViewById(R.id.etTituloPubAdd)
        etDescPubAdd = findViewById(R.id.etDescPubAdd)
        etLugarPubAdd = findViewById(R.id.etLugarPubAdd)
        etFechaPubAdd = findViewById(R.id.etFechaPubAdd)
        etHoraPubAdd = findViewById(R.id.etHoraPubAdd)
        etPrecioPubAdd = findViewById(R.id.etPrecioPubAdd)
        etImagePubAdd = findViewById(R.id.etImagePubAdd)
        btFormPubAdd = findViewById(R.id.btFormPubAdd)

        selectedDate.timeZone = TimeZone.getTimeZone("America/Lima")

        loadSpinnerData()

        etFechaPubAdd.setOnClickListener {
            Log.d("AddPubEmpActivity", "Fecha EditText clicked")
            showDatePickerDialog()
        }

        etHoraPubAdd.setOnClickListener {
            Log.d("AddPubEmpActivity", "Hora EditText clicked")
            showTimePickerDialog()
        }

        btFormPubAdd.setOnClickListener {
            addActivity()
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
                spTipoPubAdd.adapter = typeAddAdapter
            }
            .addOnFailureListener { exception ->
                Toast.makeText(this, "Error al cargar tipos de actividades: ${exception.message}", Toast.LENGTH_SHORT).show()
            }
    }

    private fun showDatePickerDialog() {
        val currentDate = Calendar.getInstance()
        DatePickerDialog(
            this,
            { _, year, month, dayOfMonth ->
                selectedDate.set(year, month, dayOfMonth)
                val dateFormat = SimpleDateFormat("yyyy/MM/dd", Locale.getDefault())
                etFechaPubAdd.setText(dateFormat.format(selectedDate.time))
                Log.d("AddPubEmpActivity", "Selected date: ${dateFormat.format(selectedDate.time)}")
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
                val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
                etHoraPubAdd.setText(timeFormat.format(selectedDate.time))
                Log.d("AddPubEmpActivity", "Selected time: ${timeFormat.format(selectedDate.time)}")
            },
            currentTime.get(Calendar.HOUR_OF_DAY),
            currentTime.get(Calendar.MINUTE),
            true
        ).show()
    }

    private fun addActivity(){
        val titulo = etTituloPubAdd.text.toString()
        val description = etDescPubAdd.text.toString()
        val lugar = etLugarPubAdd.text.toString()
        val priceText = etPrecioPubAdd.text.toString()
        val image = etImagePubAdd.text.toString()
        val type = spTipoPubAdd.selectedItem.toString()

        if (titulo.isEmpty() || description.isEmpty() || lugar.isEmpty() || etFechaPubAdd.text.isEmpty() || etHoraPubAdd.text.isEmpty() || priceText.isEmpty() || image.isEmpty()) {
            Toast.makeText(this, "Por favor completa todos los campos", Toast.LENGTH_SHORT).show()
            return
        }
        val price: Double
        try {
            price = priceText.toDouble()
        } catch (e: NumberFormatException) {
            Toast.makeText(this, "El precio debe ser un número válido", Toast.LENGTH_SHORT).show()
            return
        }

        selectedDate.timeZone = TimeZone.getTimeZone("America/Lima")

        // Convertir la fecha y hora seleccionada a Timestamp
        val timestamp = Timestamp(selectedDate.time)

        val activity = PubAddModel(
            titulo = titulo,
            description = description,
            lugar = lugar,
            time = timestamp,
            type = type,
            price = price,
            image = image,
            state = "Activo"
        )

        val db = FirebaseFirestore.getInstance()

        db.collection("activities")
            .add(activity)
            .addOnSuccessListener {
                Toast.makeText(this, "Activity data saved successfully", Toast.LENGTH_SHORT).show()
                clearFields()
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Error saving Activity data: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

    private fun clearFields() {
        etTituloPubAdd.text.clear()
        etDescPubAdd.text.clear()
        etLugarPubAdd.text.clear()
        etFechaPubAdd.text.clear()
        etHoraPubAdd.text.clear()
        spTipoPubAdd.setSelection(0)
        etPrecioPubAdd.text.clear()
        etImagePubAdd.text.clear()
    }
}
