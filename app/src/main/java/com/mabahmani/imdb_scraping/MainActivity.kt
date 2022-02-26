package com.mabahmani.imdb_scraping

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.mabahmani.imdb_scraping.databinding.ActivityMainBinding
import com.mabahmani.imdb_scraping.ui.main.home.HomeFragment
import dagger.hilt.android.AndroidEntryPoint
import io.github.inflationx.viewpump.ViewPumpContextWrapper

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setTheme(R.style.Theme_IMDbScraping)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        setupBottomNavigation()
    }

    private fun setupBottomNavigation() {
        val navHostFragment = supportFragmentManager.findFragmentById(
            R.id.nav_host_container
        ) as NavHostFragment
        val navController = navHostFragment.navController
        binding.bottomNavigation.setupWithNavController(navController)

        binding.bottomNavigation.setOnItemReselectedListener {

            when (it.itemId) {
                R.id.home -> {

                    if (navHostFragment.findNavController().currentDestination?.id == R.id.homeFragment) {
                        try {
                            (navHostFragment.childFragmentManager.fragments[0] as HomeFragment).scrollTop()
                        }catch (ex: Exception){
                            ex.printStackTrace()
                        }
                    }

                    else{
                        navHostFragment.findNavController().popBackStack(R.id.homeFragment, false)
                    }
                }
                R.id.search -> {
                    navHostFragment.findNavController().popBackStack(R.id.searchFragment, false)
                }
                R.id.charts -> {
                    navHostFragment.findNavController().popBackStack(R.id.chartsFragment , false)
                }
                R.id.trailers -> {
                    navHostFragment.findNavController().popBackStack(R.id.trailersFragment, false)
                }

            }

        }
    }

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase!!))
    }
}