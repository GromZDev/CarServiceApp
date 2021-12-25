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
    private lateinit var bottomCompanyNavigation: BottomNavigationView
    lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        bottom_navigation = binding.bottomNavigation
        bottomCompanyNavigation = binding.bottomCompanyNavigation
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_main_fragment) as NavHostFragment
        navController = navHostFragment.navController

        initBottomNavigation()
        initCompanyBottomNavigation()
    }

    private fun initBottomNavigation() {

        bottom_navigation.setOnItemSelectedListener {
            when (it.itemId) {

                R.id.action_profile -> {
                    navController.navigate(R.id.profileFragment)
                    true
                }
                R.id.action_main_screen -> {
                    navController.navigate(R.id.mainUserFragment)
                    true
                }
                R.id.action_request_services -> {
                    navController.navigate(R.id.requestServicesFragment)
                    true
                }
                R.id.action_person_map -> {
                    navController.navigate(R.id.personMapFragment)
                    true
                }
                R.id.action_more -> {
                    navController.navigate(R.id.moreMenuFragment)
                    true
                }

                else -> true
            }
        }
    }

    private fun initCompanyBottomNavigation() {

        bottomCompanyNavigation.setOnItemSelectedListener {
            when (it.itemId) {

                R.id.action_company_profile -> {
                    navController.navigate(R.id.mainCompanyPageFragment)
                    true
                }
                R.id.action_announcements -> {
                    navController.navigate(R.id.companyAnnouncementsFragment)
                    true
                }
                R.id.action_show_clients_announce -> {
                    navController.navigate(R.id.showClientsAnnounceFragment)
                    true
                }
                R.id.action_find_client -> {
                    navController.navigate(R.id.findClientFragment)
                    true
                }
                R.id.action_company_more -> {
                    navController.navigate(R.id.moreCompanyMenuFragment)
                    true
                }

                else -> true
            }
        }
    }
}