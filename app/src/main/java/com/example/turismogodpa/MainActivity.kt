package com.example.turismogodpa

import android.os.Bundle
import android.view.MenuItem
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

        // Cambia el título del ActionBar según el destino actual
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.inicioFragment -> supportActionBar?.title = "Bienvenido a Turismo Godpa"
                R.id.reservasFragment -> supportActionBar?.title = "Mis Reservas"
                R.id.cuentaFragment -> supportActionBar?.title = "Mi Cuenta"
                R.id.publicacionFragment -> supportActionBar?.title = "Publicar Actividad"
                R.id.historyeFragment -> supportActionBar?.title = "Historial de Actividades"
                R.id.cuentaCFragment -> supportActionBar?.title = "Mi Cuenta"
                else -> supportActionBar?.title = "Mi Aplicación"
            }
        }
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        return item.onNavDestinationSelected(navController) || super.onOptionsItemSelected(item)
    }

}