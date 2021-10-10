package divyansh.tech.animeclassroom.favorites.utils

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import divyansh.tech.animeclassroom.favorites.screens.DynamicTabFragment
import divyansh.tech.animeclassroom.favorites.screens.animes.FavoriteAnimeFragment


class ViewPagerAdapter(private val list: MutableList<Fragment> = mutableListOf(),
                       fragmentManager: FragmentManager,
                       lifecycle: Lifecycle
): FragmentStateAdapter(fragmentManager , lifecycle) {

    private val pageIds= mutableListOf<Long>()

    override fun getItemCount(): Int {
        return list.size
    }

    override fun createFragment(position: Int): Fragment {
        return list[position]
    }

    fun addFragments(){
        pageIds.clear()
        list.add(FavoriteAnimeFragment.getInstance())
        list.add(DynamicTabFragment.getInstance("Mangas"))
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