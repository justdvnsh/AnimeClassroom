package divyansh.tech.animeclassroom.favorites.screens.manga.epoxy

import androidx.databinding.ViewDataBinding
import com.airbnb.epoxy.DataBindingEpoxyModel
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import divyansh.tech.animeclassroom.R
import divyansh.tech.animeclassroom.BR
import divyansh.tech.animeclassroom.common.AnimeClickCallback
import divyansh.tech.animeclassroom.mangaModels.Manga

@EpoxyModelClass(layout = R.layout.recycler_item_favorite_manga)
abstract class EpoxyFavoriteMangaModel: DataBindingEpoxyModel() {

    @EpoxyAttribute
    lateinit var manga: Manga

    @EpoxyAttribute
    lateinit var mangaCallback: AnimeClickCallback

    override fun setDataBindingVariables(binding: ViewDataBinding) {
        binding.setVariable(BR.manga, manga)
        binding.setVariable(BR.mangaCallback, mangaCallback)
    }
}