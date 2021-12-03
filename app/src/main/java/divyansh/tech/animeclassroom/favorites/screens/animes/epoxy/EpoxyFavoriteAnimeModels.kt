package divyansh.tech.animeclassroom.favorites.screens.animes.epoxy

import androidx.databinding.ViewDataBinding
import com.airbnb.epoxy.DataBindingEpoxyModel
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import divyansh.tech.animeclassroom.BR
import divyansh.tech.animeclassroom.R
import divyansh.tech.animeclassroom.common.AnimeClickCallback
import divyansh.tech.animeclassroom.common.data.home.OfflineAnimeModel

@EpoxyModelClass(layout = R.layout.recycler_item_favorite_anime)
abstract class EpoxyFavoriteAnimeModel : DataBindingEpoxyModel() {

    @EpoxyAttribute
    lateinit var anime: OfflineAnimeModel

    @EpoxyAttribute
    lateinit var animeCallback: AnimeClickCallback

    override fun setDataBindingVariables(binding: ViewDataBinding) {
        binding.setVariable(BR.anime, anime)
        binding.setVariable(BR.animeCallback, animeCallback)
    }
}