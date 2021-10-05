package divyansh.tech.animeclassroom.favorites

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import divyansh.tech.animeclassroom.R
import divyansh.tech.animeclassroom.databinding.FragmentFavoritesBinding
import divyansh.tech.animeclassroom.favorites.utils.ViewPagerAdapter

@AndroidEntryPoint
class FavoritesFragment: Fragment() {

    private lateinit var _favoritesFragmentBinding : FragmentFavoritesBinding
    val binding : FragmentFavoritesBinding get() = _favoritesFragmentBinding
    lateinit var pagerAdapter: ViewPagerAdapter
    private var mediator: TabLayoutMediator? = null
    val tabNames= listOf("Animes","Mangas")

    private val pageChangeCallback = object : ViewPager2.OnPageChangeCallback() {

        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _favoritesFragmentBinding = FragmentFavoritesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
        setUpViewpager()
    }

    private fun setupViews() {
        binding.toolbar.goBack.visibility = View.GONE
        binding.toolbar.title.text = getString(R.string.favorites)
    }

    private fun setUpViewpager(){

        pagerAdapter = ViewPagerAdapter(fragmentManager = childFragmentManager, lifecycle = lifecycle)

        binding.viewPager2.isUserInputEnabled = true

        binding.viewPager2.adapter = pagerAdapter
        binding.viewPager2.registerOnPageChangeCallback(pageChangeCallback)

        if (mediator != null)
            mediator!!.detach()
        pagerAdapter.removeAllFragments()
        pagerAdapter.addFragments(tabNames)
        binding.viewPager2.offscreenPageLimit = pagerAdapter.itemCount

        val tabs = mutableListOf<String>().apply {
            addAll(tabNames)
        }

        val strategy =
            TabLayoutMediator.TabConfigurationStrategy { tab, position ->
                tab.text = tabs[position]
            }

        mediator = TabLayoutMediator(
            binding.tabLayout,
            binding.viewPager2,
            strategy
        )

        mediator?.attach()
    }

    override fun onStop() {
        binding.viewPager2.unregisterOnPageChangeCallback(pageChangeCallback)
        super.onStop()
    }


}