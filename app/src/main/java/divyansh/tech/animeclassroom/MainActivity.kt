package divyansh.tech.animeclassroom

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
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
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

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
        NavigationUI.setupWithNavController(bottomNavigation, navHostFragment.findNavController())
        navHostFragment.findNavController().addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.homeFragment -> bottomNavigation.visibility = View.VISIBLE
                R.id.favoritesFragment -> bottomNavigation.visibility = View.VISIBLE
                R.id.mangaFragment -> bottomNavigation.visibility = View.VISIBLE
                R.id.shopFragment -> bottomNavigation.visibility = View.VISIBLE
                R.id.settingsFragment -> bottomNavigation.visibility = View.VISIBLE
                R.id.animeDetailFragment -> bottomNavigation.visibility = View.VISIBLE
                else -> bottomNavigation.visibility = View.GONE
            }
        }
    }
}