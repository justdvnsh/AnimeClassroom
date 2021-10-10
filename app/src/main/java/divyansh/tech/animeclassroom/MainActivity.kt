package divyansh.tech.animeclassroom

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import dagger.hilt.android.AndroidEntryPoint
import divyansh.tech.animeclassroom.favorites.FavoritesFragment
import divyansh.tech.animeclassroom.home.HomeFragment
import divyansh.tech.animeclassroom.manga.MangaFragment
import divyansh.tech.animeclassroom.settings.SettingsFragment
import divyansh.tech.animeclassroom.shop.ShopFragment
import kotlinx.android.synthetic.main.activity_main.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupNavigation()
    }

    /*
    * Helper method to setup the bottom navigation
    */
    private fun setupNavigation() {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.mainNavHost) as NavHostFragment
        navController = navHostFragment.findNavController()
        NavigationUI.setupWithNavController(bottomNavigation, navHostFragment.findNavController())
        navHostFragment.findNavController().addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.homeFragment -> bottomNavigation.visibility = View.VISIBLE
                R.id.favoritesFragment -> bottomNavigation.visibility = View.VISIBLE
                R.id.mangaFragment -> bottomNavigation.visibility = View.VISIBLE
                R.id.shopFragment -> bottomNavigation.visibility = View.VISIBLE
                R.id.settingsFragment -> bottomNavigation.visibility = View.VISIBLE
//                R.id.animeDetailFragment -> bottomNavigation.visibility = View.VISIBLE
                else -> bottomNavigation.visibility = View.GONE
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}