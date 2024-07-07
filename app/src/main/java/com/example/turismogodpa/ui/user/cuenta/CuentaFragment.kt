package com.example.turismogodpa.ui.user.cuenta

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.lifecycleScope
import com.example.turismogodpa.R
import com.example.turismogodpa.data.model.UserProfile
import com.example.turismogodpa.databinding.FragmentCuentaBinding
import com.example.turismogodpa.ui.autentication.LoginActivity
import com.example.turismogodpa.ui.autentication.dataStore
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class CuentaFragment : Fragment() {
    private lateinit var binding: FragmentCuentaBinding
    private var idUser: String? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val View:View= inflater.inflate(R.layout.fragment_cuenta, container, false)
        binding = FragmentCuentaBinding.bind(View)

        val db = FirebaseFirestore.getInstance()

        lifecycleScope.launch(Dispatchers.IO) {
            getUserProfile().collect {
                withContext(Dispatchers.Main) {
                    idUser = it.userId
                    //obterner datos del usuario de firestore con el idUser obtenido
                    db.collection("users").document(idUser!!)
                        .get()
                        .addOnSuccessListener { document ->
                            if (document != null) {
                                binding.etNameusr.setText(document.getString("name"))
                                binding.etLastNameusr.setText(document.getString("lastname"))
                                binding.etDni.setText(document.getString("dni"))
                                binding.etEmailusr.setText(document.getString("email"))
                                binding.etPhoneusr.setText(document.getString("phone"))
                                binding.etFechaNacusr.setText(document.getString("fechaNac"))




                            }
                        }
                        .addOnFailureListener { exception ->
                            //Log.e("ERROR-FIREBASE", "Error getting documents: ", exception)
                        }


                }
            }
        }

        return View


    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btActualizarPerfilUsuario.setOnClickListener{
            val rootView : View = requireActivity().findViewById(android.R.id.content)
            //Snackbar.make(rootView, "Perfil Actualizado", Snackbar.LENGTH_LONG).show()
        }

        binding.btDesactivarPerfil.setOnClickListener{
            val rootView : View = requireActivity().findViewById(android.R.id.content)
            //Snackbar.make(rootView, "Perfil Desactivado", Snackbar.LENGTH_LONG).show()
        }
        binding.btCerrarSesion.setOnClickListener{
            val rootView : View = requireActivity().findViewById(android.R.id.content)

            // Cerrar sesion
            lifecycleScope.launch {
                requireContext().dataStore.edit { preferences ->
                    preferences[stringPreferencesKey("userId")] = ""
                    preferences[stringPreferencesKey("email")] = ""
                    preferences[stringPreferencesKey("name")] = ""
                    //cuando se cierre sesion se debe redirigir a la pantalla de login y limpiar el backstack
//                    val intent = Intent(requireContext(), LoginActivity::class.java)
//                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                }
            }


        }
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