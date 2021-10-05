package divyansh.tech.animeclassroom.favorites

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import divyansh.tech.animeclassroom.R
import divyansh.tech.animeclassroom.databinding.FragmentFavoritesBinding

@AndroidEntryPoint
class FavoritesFragment: Fragment() {

    private lateinit var _favoritesFragmentBinding : FragmentFavoritesBinding
    val binding : FragmentFavoritesBinding get() = _favoritesFragmentBinding
    lateinit var pagerAdapter: ViewPagerAdapter
    private var mediator: TabLayoutMediator? = null
    val tabNames= listOf("Animes","Mangas")

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

    private val pageChangeCallback = object : ViewPager2.OnPageChangeCallback() {

        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)
        }
    }

    class ViewPagerAdapter( private val list: MutableList<Fragment> = mutableListOf(),
                            fragmentManager: FragmentManager ,
                            lifecycle: Lifecycle
    ): FragmentStateAdapter(fragmentManager , lifecycle) {

        private val pageIds= mutableListOf<Long>()

        override fun getItemCount(): Int {
            return list.size
        }

        override fun createFragment(position: Int): Fragment {
            return list[position]
        }

        fun addFragments(ls:List<String>){
            pageIds.clear()
            ls.forEach {
                    category ->
                list.add(DynamicTabFragment.getInstance(category))
            }

            pageIds.addAll(list.map { it.hashCode().toLong() })
            notifyDataSetChanged()
        }

        fun removeAllFragments(){
            list.clear()
            pageIds.clear()
            notifyDataSetChanged()
        }

        override fun getItemId(position: Int): Long {
            return pageIds[position]
        }

        override fun containsItem(itemId: Long): Boolean {
            return pageIds.contains(itemId)
        }


    }


}