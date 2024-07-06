package com.example.turismogodpa

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.turismogodpa.adapter.UserHistEmpAdapter
import com.example.turismogodpa.data.UserHistEmpData
import com.google.firebase.firestore.FirebaseFirestore

class PubHistUsuariosEmpActivity : AppCompatActivity() {
    private lateinit var adapter: UserHistEmpAdapter
    private val userList = mutableListOf<UserHistEmpData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_pub_hist_usuarios_emp)
        supportActionBar?.hide()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val rvHistUser: RecyclerView = findViewById(R.id.rvHistUser)
        rvHistUser.layoutManager = LinearLayoutManager(this)
        adapter = UserHistEmpAdapter(userList)
        rvHistUser.adapter = adapter

        // Obtener el UID de la actividad desde el intent
        val uid = intent.getStringExtra("UID") ?: return
        Log.d("PubHistUsuariosEmpActivity", "Received UID: $uid")

        // Obtener usuarios inscritos desde Firestore
        fetchUsers(uid)
    }

    private fun fetchUsers(activityId: String) {
        val db = FirebaseFirestore.getInstance()
        db.collection("bookings2")
            .whereEqualTo("idactivity", activityId)
            .get()
            .addOnSuccessListener { documents ->
                if (documents.isEmpty) {
                    Log.d("PubHistUsuariosEmpActivity", "No bookings found for activity ID: $activityId")
                }
                for (document in documents) {
                    val userId = document.getString("iduser")
                    if (userId != null) {
                        Log.d("PubHistUsuariosEmpActivity", "Found booking for user ID: $userId")
                        fetchUserDetails(userId)
                    }
                }
            }
            .addOnFailureListener { e ->
                Log.w("PubHistUsuariosEmpActivity", "Error getting bookings", e)
            }
    }

    private fun fetchUserDetails(userId: String) {
        val db = FirebaseFirestore.getInstance()
        db.collection("users").document(userId).get()
            .addOnSuccessListener { userDoc ->
                if (userDoc != null) {
                    val user = userDoc.toObject(UserHistEmpData::class.java)
                    if (user != null) {
                        Log.d("PubHistUsuariosEmpActivity", "Fetched user details: $user")
                        userList.add(user)
                        adapter.notifyDataSetChanged()
                    } else {
                        Log.w("PubHistUsuariosEmpActivity", "User details are null for user ID: $userId")
                    }
                } else {
                    Log.w("PubHistUsuariosEmpActivity", "No user document found for user ID: $userId")
                }
            }
            .addOnFailureListener { e ->
                Log.w("PubHistUsuariosEmpActivity", "Error getting user document", e)
            }
    }
}