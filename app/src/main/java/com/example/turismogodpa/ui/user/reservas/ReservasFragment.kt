package com.example.turismogodpa.ui.user.reservas

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.datastore.dataStore
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.turismogodpa.R
import com.example.turismogodpa.adapter.MisReservasAdapter
import com.example.turismogodpa.data.model.ActividadesHomeModel
import com.example.turismogodpa.data.model.CompanyModel
import com.example.turismogodpa.data.model.MisReservasModel
import com.example.turismogodpa.data.model.UserModel
import com.example.turismogodpa.data.model.UserProfile
import com.example.turismogodpa.databinding.FragmentReservasBinding
import com.example.turismogodpa.ui.actividadTu.DetalleActividadActivity
import com.example.turismogodpa.ui.autentication.dataStore
import com.example.turismogodpa.ui.review.AddReviewActivity
import com.google.android.gms.tasks.Tasks
import com.google.firebase.firestore.FirebaseFirestore
import com.squareup.picasso.Picasso
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Arrays


class ReservasFragment : Fragment() {

    private lateinit var binding: FragmentReservasBinding
    private var idUser: String? = null

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        // Inflate the layout for this fragment
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        binding = FragmentReservasBinding.inflate(inflater, container, false)
        val view = binding.root
        //val rvMisReservas: RecyclerView = binding.rvMR
        val rvMisReservas: RecyclerView = view.findViewById(R.id.rvMR)
        val db = FirebaseFirestore.getInstance()
        var misReservasList: List<MisReservasModel>


        //Corrutina para obtener usuario
        lifecycleScope.launch(Dispatchers.IO) {
            getUserProfile().collect {
                withContext(Dispatchers.Main) {
                    idUser = it.userId
                    Log.e("ID USER", "ID USUARIO EN ACTIVIDAD RESERVA: $idUser")

                    db.collection("activities").whereArrayContains("users", "$idUser")
                        .addSnapshotListener{ snap, errror ->
                            if (errror != null){
                                Log.e("ERROR-FIREBASE", "Ocurrio error: ${errror.message}")
                                return@addSnapshotListener
                            }
                            misReservasList = snap!!.documents.map{document ->
                                MisReservasModel(
                                    document["Description"].toString(),
                                    document["Lugar"].toString(),
                                    document["date"].toString(),
                                    document["idCompany"].toString(),
                                    document["image"].toString(),
                                    document["price"].toString(),
                                    document["state"].toString(),
                                    document["hora"].toString(),
                                    document["titulo"].toString(),
                                    document["type"].toString(),
                                    document["users"].toString()
                                )}
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

                                }
                            })
                            rvMisReservas.layoutManager = LinearLayoutManager(requireContext())
                        }
                }
            }
        }


        val deco = DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
        deco.setDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.divider) ?: requireContext().resources.getDrawable(R.drawable.divider, requireContext().theme))
        rvMisReservas.addItemDecoration(deco)





        binding.btDRComentario.setOnClickListener {
            val intent = Intent(requireContext(), AddReviewActivity::class.java)
            startActivity(intent)
        }

        binding.ivVolverDR.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
        binding.tvVolverDR.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }



        return view
    }

        //Funcion obtener usuario
        private fun getUserProfile() = requireContext().dataStore.data.map{ preferences ->
            UserProfile(
                name = preferences[stringPreferencesKey("name")].orEmpty(),
                email = preferences[stringPreferencesKey("email")].orEmpty(),
                userId = preferences[stringPreferencesKey("userId")].orEmpty()

            )

        }


}