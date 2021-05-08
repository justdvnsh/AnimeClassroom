package divyansh.tech.animeclassroom.home.epoxy

import androidx.databinding.ViewDataBinding
import com.airbnb.epoxy.DataBindingEpoxyModel
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import divyansh.tech.animeclassroom.BR
import divyansh.tech.animeclassroom.R
import divyansh.tech.animeclassroom.models.home.AnimeModel
import divyansh.tech.animeclassroom.models.home.GenreModel

/*
* Title Class For Header Titles
* */
@EpoxyModelClass(layout = R.layout.reycler_item_title)
abstract class EpoxyTitleModel: DataBindingEpoxyModel() {

    @EpoxyAttribute
    lateinit var headerTitle: String

    override fun setDataBindingVariables(binding: ViewDataBinding) {
        binding.setVariable(BR.headerTitle, headerTitle)
    }
}

/*
* Epoxy class for Anime Models
* */
@EpoxyModelClass(layout = R.layout.recycler_item_anime_models)
abstract class EpoxyAnimeModels: DataBindingEpoxyModel() {

    @EpoxyAttribute
    lateinit var anime: AnimeModel

    override fun setDataBindingVariables(binding: ViewDataBinding) {
         binding.setVariable(BR.anime, anime)
    }
}

/*
* Epoxy class for Anime Models
* */
@EpoxyModelClass(layout = R.layout.recycler_item_anime_meta_model)
abstract class EpoxyAnimeMetaModels: DataBindingEpoxyModel() {

    @EpoxyAttribute
    lateinit var anime: AnimeModel

    override fun setDataBindingVariables(binding: ViewDataBinding) {
        binding.setVariable(BR.anime, anime)
    }
}

/*
* Epoxy Models for Genres
* */
@EpoxyModelClass(layout = R.layout.recyler_item_genre_home)
abstract class EpoxyGenreModels: DataBindingEpoxyModel() {

    @EpoxyAttribute
    lateinit var genre: GenreModel

    override fun setDataBindingVariables(binding: ViewDataBinding) {
        binding.setVariable(BR.genre, genre)
    }
}

