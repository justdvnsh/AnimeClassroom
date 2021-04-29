package divyansh.tech.animeclassroom

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
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

    @Inject lateinit var homeFragment: HomeFragment
    @Inject lateinit var favoritesFragment: FavoritesFragment
    @Inject lateinit var mangaFragment: MangaFragment
    @Inject lateinit var shopFragment: ShopFragment
    @Inject lateinit var settingsFragment: SettingsFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupFragment(homeFragment)
        setupBottomBar()
    }

    /*
    * Helper method to setup the bottom navigation
    * */
    private fun setupBottomBar() {
        bottomNavigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.homeFragment -> setupFragment(homeFragment)
                R.id.favoritesFragment -> setupFragment(favoritesFragment)
                R.id.mangaFragment -> setupFragment(mangaFragment)
                R.id.shopFragment -> setupFragment(shopFragment)
                R.id.settingsFragment -> setupFragment(settingsFragment)
            }
            true
        }
    }

    /*
    * Helper method to setup the fragments
    * */
    private fun setupFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.mainNavHost, fragment)
            commit()
        }
    }
}