package com.example.turismogodpa.ui.user.reservas

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import com.example.turismogodpa.R
import com.example.turismogodpa.databinding.FragmentReservasBinding


class ReservasFragment : Fragment() {

    private lateinit var binding: FragmentReservasBinding

    override fun onCreateView(
        // Inflate the layout for this fragment
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_reservas, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Se usa para vincular las vistas del fragmento, permite acceder a ellas sin necesidad de usar findViewById
        //accede directamente a las vistas  definidad en el xml
        binding = FragmentReservasBinding.bind(view)


        events()
    }

    private fun events() = with(binding) {

        //Spinner Numero Personas
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.NumPersonas_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spRNumPersonas.adapter = adapter
        }

        //Spinner Fecha
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.FechaReservas_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spRFecha.adapter = adapter
        }

        //Spinner Hora
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.HoraReservas_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spRHora.adapter = adapter
        }

        var spRNumPersonasValue: String = ""
        var spRFechaValue: String = ""
        var spRHoraValue: String = ""

        //Llenar Spinner Numero Personas
        spRNumPersonas.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
                spRNumPersonasValue = parent?.getItemAtPosition(position).toString()
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }

        //Llenar Spinner Fecha
        spRFecha.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
                spRFechaValue = parent?.getItemAtPosition(position).toString()
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }

        //Llenar Spinner Hora
        spRHora.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
                spRHoraValue = parent?.getItemAtPosition(position).toString()
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }


        btReservar.setOnClickListener{
            containerReservar.visibility = View.GONE
            ResumenReserva.visibility = View.VISIBLE

        }
        tvVolverR.setOnClickListener{
            //val transaction = requireActivity().supportFragmentManager.beginTransaction()
            //transaction.replace(R.id.container, ReservasFragment())
            //transaction.commit()
            containerReservar.visibility = View.VISIBLE
            ResumenReserva.visibility = View.GONE
        }
        ivVolverR.setOnClickListener{
            containerReservar.visibility = View.VISIBLE
            ResumenReserva.visibility = View.GONE
        }


        //Confirmar Reserva

        tvRRIdUsuario.text = "pguerrero@peru.com"
        tvRRNombreContacto.text = "Paolo Guerrero"
        tvRRActvidad.text = "Caminata"
        tvRREmpresa.text = "Turismo Peru"
        tvRRFecha.text = "2021-09-30"

        btConfirmarRR.setOnClickListener {
            val dialog = Dialog(requireContext())
            dialog.setContentView(R.layout.dialog_reserva_ok) // Use the correct layout for the dialog

            val btDialogR: Button = dialog.findViewById(R.id.btnOkDialogReserva)
            dialog.show()

            btDialogR.setOnClickListener {
                dialog.dismiss()
                etRNombreContacto.setText("")
                etRTelefono.setText("")
                containerReservar.visibility = View.VISIBLE
                ResumenReserva.visibility = View.GONE
            }
        }


    }


}