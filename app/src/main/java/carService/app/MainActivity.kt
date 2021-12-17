package carService.app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import carService.app.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    private lateinit var bottom_navigation: BottomNavigationView
    lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        bottom_navigation = binding.bottomNavigation
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_main_fragment) as NavHostFragment
        navController = navHostFragment.navController

        initBottomNavigation()
    }

    private fun initBottomNavigation() {

        bottom_navigation.setOnItemSelectedListener {
            when (it.itemId) {

                R.id.action_profile -> {
                    navController.navigate(R.id.profileFragment)
                    true
                }
                R.id.action_services -> {
                    navController.navigate(R.id.servicesFragment)
                    true
                }
                R.id.action_products -> {
                    navController.navigate(R.id.mainUserFragment)
                    true
                }
                R.id.action_stock -> {
                    navController.navigate(R.id.stockFragment)
                    true
                }
                R.id.action_settings -> {
                    navController.navigate(R.id.settingsFragment)
                    true
                }

                else -> true
            }
        }
    }
}