package com.example.turismogodpa

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.turismogodpa.adapter.UserHistEmpAdapter
import com.example.turismogodpa.data.UserHistEmpData

class PubHistUsuariosEmpActivity : AppCompatActivity() {
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
        rvHistUser.adapter = UserHistEmpAdapter(UserHistEmpList())
    }
    private fun UserHistEmpList(): List<UserHistEmpData>{
        val lstUseHistEmp: ArrayList<UserHistEmpData> = ArrayList()

        lstUseHistEmp.add(
            UserHistEmpData("Carlos"
                , "Ramirez"
                , "45555411"
                , "cramirez@gmail.com"
            )
        )

        lstUseHistEmp.add(
            UserHistEmpData("Marta"
                , "Berrocal"
                , "74410004"
                , "mbrr@gmail.com"
            )
        )

        lstUseHistEmp.add(
            UserHistEmpData("Gabriela"
                , "Cortez"
                , "74115828"
                , "gcortez@gmail.com"
            )
        )

        return lstUseHistEmp

    }
}