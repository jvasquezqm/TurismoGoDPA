package com.example.turismogodpa

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.turismogodpa.fragments.ComentPubDiaFragment
import com.google.android.material.floatingactionbutton.FloatingActionButton

class PubDetalleEmpActivity : AppCompatActivity() {

    //val imbUserPubDet: imageButton = view.findViewById(R.id.imbUserPubDet)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_pub_detalle_emp)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val imbUserPubDet: ImageButton = findViewById(R.id.imbUserPubDet)

        imbUserPubDet.setOnClickListener {
            // Aquí defines la lógica para abrir el otro Activity
            val intent = Intent(this, PubHistUsuariosEmpActivity::class.java)
            startActivity(intent)
        }
        val imbComentPubDet: ImageButton = findViewById(R.id.imbComentPubDet)
        imbComentPubDet.setOnClickListener {
            val dialog = ComentPubDiaFragment()
            dialog.show(supportFragmentManager, "CommentDialogFragment")
        }

    }
}