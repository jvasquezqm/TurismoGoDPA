package com.example.turismogodpa.ui.user.reservas

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

        button.setOnClickListener{
            container1.visibility = View.GONE
            container2.visibility = View.VISIBLE

        }

    }


}