package com.example.groupizer.ui.auth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.groupizer.R
import com.example.groupizer.databinding.ActivityAuthBinding

class AuthActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAuthBinding
    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)
        setSupportActionBar(binding.mainToolbar)
        val navHost = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHost.navController

        setupActionBarWithNavController(navController)


        /*
            A listener set for listening to changes in fragments current destination
         */
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.homeFragment -> {
                    binding.mainToolbar.visibility = View.GONE
                }
                R.id.loginFragment -> {
                    binding.mainToolbar.visibility = View.VISIBLE
                }
                R.id.registerFragment -> {
                    binding.mainToolbar.visibility = View.VISIBLE
                }
                R.id.interestFragment -> {
                    binding.mainToolbar.visibility = View.VISIBLE
                }

            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}