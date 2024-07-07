package com.example.turismogodpa.ui.company.publicacion

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.turismogodpa.AddPubEmpActivity
import com.example.turismogodpa.R
import com.example.turismogodpa.adapter.PubAdapter
import com.example.turismogodpa.data.PubResumData
import com.example.turismogodpa.data.model.UserProfile
import com.example.turismogodpa.ui.autentication.dataStore
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking

class PublicacionFragment : Fragment(), PubAdapter.OnImageButtonClickListener {
    private var idCompany: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_pub, container, false)
        val db = FirebaseFirestore.getInstance()
        val btAddPub: FloatingActionButton = view.findViewById(R.id.btAddPub)
        val rvPublicaciones: RecyclerView = view.findViewById(R.id.rvPublicaciones)
        var pubList: List<PubResumData>

        runBlocking {
            idCompany = getUserProfile().first().userId

        }
        println("IDCOMPANY $idCompany")
        val companyRef: DocumentReference = db.collection("users").document(idCompany!!)

        db.collection("activities")
            .orderBy("titulo")
            .whereEqualTo("state", "Activo")
            .whereEqualTo("idCompany", companyRef)
            .addSnapshotListener { snap, error ->
                if (error != null) {
                    Log.e("ERROR-FIREBASE", "Detalle del error: ${error.message}")
                    return@addSnapshotListener
                }

                pubList = snap!!.documents.map { document ->
                    PubResumData(
                        document["image"].toString(),
                        document["titulo"].toString(),
                        document["description"].toString(),
                        document.id,
                        document["state"].toString()
                    )
                }
                rvPublicaciones.adapter = PubAdapter(pubList, this)
                rvPublicaciones.layoutManager = LinearLayoutManager(requireContext())
            }

        // Configurar el OnClickListener para el botón flotante
        btAddPub.setOnClickListener {
            val intent = Intent(requireContext(), AddPubEmpActivity::class.java)
            startActivity(intent)
        }

        return view
    }

    override fun onImageButtonClick(documentId: String) {
        val intent = Intent(requireContext(), UpdatePubEmpActivity::class.java)
        intent.putExtra("DOCUMENT_ID", documentId)
        startActivity(intent)
    }

    private fun getUserProfile() = requireContext().dataStore.data.map { preferences ->
        UserProfile(
            name = preferences[stringPreferencesKey("name")].orEmpty(),
            email = preferences[stringPreferencesKey("email")].orEmpty(),
            userId = preferences[stringPreferencesKey("userId")].orEmpty()
        )
    }
}
