package com.example.turismogodpa

import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.onNavDestinationSelected
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.turismogodpa.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        // Infla el menú correspondiente dependiendo del tipo de usuario
        val userType = intent.getStringExtra("USER_TYPE")
        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        navView.menu.clear()
        if (userType == "user") {
            navView.inflateMenu(R.menu.menu_cliente)
            navController.setGraph(R.navigation.nav_cliente)
        } else if (userType == "company") {
            navView.inflateMenu(R.menu.menu_empresa)
            navController.setGraph(R.navigation.nav_empresa)
        }


        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.inicioFragment, R.id.reservasFragment, R.id.cuentaFragment, // Para cliente
                R.id.publicacionFragment, R.id.historyeFragment, R.id.cuentaCFragment, // Para empresa
                R.id.navhistorialemp, R.id.navcuentaemp
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        // En tu método onCreate
        supportActionBar?.setDisplayShowCustomEnabled(true)
        supportActionBar?.setCustomView(R.layout.action_bar_layout)
        val titleView: TextView = supportActionBar?.customView?.findViewById(R.id.action_bar_title) as TextView

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.inicioFragment -> titleView.text = "Bienvenido a Turismo Godpa"
                R.id.reservasFragment -> titleView.text = "Mis Reservas"
                R.id.cuentaFragment -> titleView.text = "Mi Cuenta"
                R.id.publicacionFragment -> titleView.text = "Publicar Actividad"
                R.id.historyeFragment -> titleView.text = "Historial de Actividades"
                R.id.cuentaCFragment -> titleView.text = "Mi Cuenta"
                else -> titleView.text = "Mi Aplicación"
            }
        }
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        return item.onNavDestinationSelected(navController) || super.onOptionsItemSelected(item)
    }

}