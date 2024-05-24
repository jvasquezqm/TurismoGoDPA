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
                R.id.publicacionFragment, R.id.historyeFragment, R.id.cuentaCFragment // Para empresa
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        return item.onNavDestinationSelected(navController) || super.onOptionsItemSelected(item)
    }

}