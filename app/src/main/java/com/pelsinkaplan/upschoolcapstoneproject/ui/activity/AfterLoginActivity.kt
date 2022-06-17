package com.pelsinkaplan.upschoolcapstoneproject.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.pelsinkaplan.upschoolcapstoneproject.R
import com.pelsinkaplan.upschoolcapstoneproject.databinding.ActivityAfterLoginBinding
import com.pelsinkaplan.upschoolcapstoneproject.databinding.ActivityBeforeLoginBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AfterLoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAfterLoginBinding
    lateinit var view: View
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAfterLoginBinding.inflate(layoutInflater)
        binding.toolbar.title = ""
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        val navHostFragment =
            supportFragmentManager.findFragmentById(binding.fragmentContainerView.id) as NavHostFragment
        val navController = navHostFragment.navController
        NavigationUI.setupWithNavController(binding.bottomNav, navController)
        val appBarConfiguration = AppBarConfiguration(
            setOf(R.id.homeFragment, R.id.categoriesFragment, R.id.chartFragment)
        )
        binding.toolbar.setupWithNavController(
            navController,
            appBarConfiguration
        )
    }

    override fun onSupportNavigateUp(): Boolean {
        applicationContext
        findNavController(R.id.fragmentContainerView).popBackStack()
        return true
    }
}