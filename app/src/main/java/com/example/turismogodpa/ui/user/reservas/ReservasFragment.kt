package com.example.turismogodpa.ui.user.reservas

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.datastore.dataStore
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.turismogodpa.R
import com.example.turismogodpa.adapter.MisReservasAdapter
import com.example.turismogodpa.data.model.MisReservasModel
import com.example.turismogodpa.data.model.UserProfile
import com.example.turismogodpa.databinding.FragmentReservasBinding
import com.example.turismogodpa.ui.autentication.dataStore
import com.example.turismogodpa.ui.review.AddReviewActivity
import com.google.android.gms.tasks.Tasks
import com.google.firebase.firestore.FirebaseFirestore
import com.squareup.picasso.Picasso
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.concurrent.CompletableFuture

class ReservasFragment : Fragment() {

    private lateinit var binding: FragmentReservasBinding
    private var idUser: String? = null

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentReservasBinding.inflate(inflater, container, false)
        val view = binding.root
        val rvMisReservas: RecyclerView = view.findViewById(R.id.rvMR)
        val db = FirebaseFirestore.getInstance()
        var misReservasList: List<MisReservasModel>

        lifecycleScope.launch(Dispatchers.IO) {
            getUserProfile().collect {
                withContext(Dispatchers.Main) {
                    idUser = it.userId
                    Log.e("ID USER", "ID USUARIO EN ACTIVIDAD RESERVA: $idUser")

                    db.collection("activities").whereArrayContains("users", "$idUser")
                        .addSnapshotListener { snap, error ->
                            if (error != null) {
                                Log.e("ERROR-FIREBASE", "Ocurrio error: ${error.message}")
                                return@addSnapshotListener
                            }
                            val tasks = snap!!.documents.mapNotNull { document ->
                                val idCompanyRef = document.getDocumentReference("idCompany")
                                idCompanyRef?.get()?.continueWith { task ->
                                    val razonSocial = if (task.isSuccessful) {
                                        task.result?.getString("razonSocial").toString()
                                    } else {
                                        ""
                                    }
                                    MisReservasModel(
                                        document.id,
                                        document["Description"].toString(),
                                        document["Lugar"].toString(),
                                        document["date"].toString(),
                                        razonSocial,
                                        document["image"].toString(),
                                        document["price"].toString(),
                                        document["state"].toString(),
                                        document["hora"].toString(),
                                        document["titulo"].toString(),
                                        document["type"].toString(),
                                        document["users"].toString()
                                    )
                                }
                            }

                            Tasks.whenAllComplete(tasks).addOnCompleteListener { completedTasks ->
                                misReservasList = completedTasks.result?.mapNotNull { completedTask ->
                                    (completedTask as? com.google.android.gms.tasks.Task<MisReservasModel>)?.result
                                } ?: emptyList()

                                Log.e("DATOS-FIREBASE", "FIREBASE CONSULTA: ${misReservasList}")

                                rvMisReservas.adapter = MisReservasAdapter(misReservasList, object : MisReservasAdapter.OnItemClickListener {
                                    override fun onItemClick(currentItem: MisReservasModel) {
                                        binding.ListaRerserva.visibility = View.GONE
                                        binding.DetalleReserva.visibility = View.VISIBLE

                                        Picasso.get()
                                            .load(currentItem.image)
                                            .resize(400, 250)
                                            .into(binding.ivDetalleReserva)
                                        binding.tvDREmpresa.text = currentItem.idCompany
                                        binding.tvDRNombre.text = currentItem.titulo
                                        binding.tvDRHora.text = currentItem.hora
                                        binding.tvDRFecha.text = currentItem.date
                                        binding.btDRComentario.setOnClickListener {
                                            val intent = Intent(requireContext(), AddReviewActivity::class.java)
                                            intent.putExtra("idactivity", currentItem.id)
                                            requireContext().startActivity(intent)
                                            Log.e("ID ACTIVITY", "ID ACTIVITY EN RESERVA FRAG: ${currentItem.id}")
                                        }
                                    }
                                })
                                rvMisReservas.layoutManager = LinearLayoutManager(requireContext())


                            }
                        }
                }
            }
        }

        val deco = DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
        deco.setDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.divider) ?: requireContext().resources.getDrawable(R.drawable.divider, requireContext().theme))
        rvMisReservas.addItemDecoration(deco)



        binding.ivVolverDR.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
        binding.tvVolverDR.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }

        return view
    }

    // Funcion obtener usuario
    private fun getUserProfile() = requireContext().dataStore.data.map { preferences ->
        UserProfile(
            name = preferences[stringPreferencesKey("name")].orEmpty(),
            email = preferences[stringPreferencesKey("email")].orEmpty(),
            userId = preferences[stringPreferencesKey("userId")].orEmpty()
        )
    }
}
