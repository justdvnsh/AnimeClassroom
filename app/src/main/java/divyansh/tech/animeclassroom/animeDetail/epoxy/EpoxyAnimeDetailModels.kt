package divyansh.tech.animeclassroom.animeDetail.epoxy

import androidx.databinding.ViewDataBinding
import androidx.databinding.library.baseAdapters.BR
import com.airbnb.epoxy.DataBindingEpoxyModel
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import divyansh.tech.animeclassroom.R
import divyansh.tech.animeclassroom.animeDetail.callbacks.EpisodeClickCallback
import divyansh.tech.animeclassroom.animeDetail.callbacks.FavoriteClickCallback
import divyansh.tech.animeclassroom.models.home.AnimeDetailModel
import divyansh.tech.animeclassroom.models.home.EpisodeModel
import divyansh.tech.animeclassroom.models.home.GenreModel

@EpoxyModelClass(layout = R.layout.fragment_anime_details_header)
abstract class EpoxyAnimeDetailHeaderModel: DataBindingEpoxyModel() {
    @EpoxyAttribute
    lateinit var anime: AnimeDetailModel

    @EpoxyAttribute
    lateinit var favoriteClickCallback: FavoriteClickCallback

    @EpoxyAttribute
    lateinit var animeUrl: String

    override fun setDataBindingVariables(binding: ViewDataBinding) {
        binding.setVariable(BR.anime, anime)
        binding.setVariable(BR.favoriteCallback, favoriteClickCallback)
        binding.setVariable(BR.animeUrl, animeUrl)
    }
}

@EpoxyModelClass(layout = R.layout.fragment_anime_detail_plot_summary)
abstract class EpoxyAnimeDetailPlotSummaryModel: DataBindingEpoxyModel() {
    @EpoxyAttribute
    lateinit var anime: AnimeDetailModel

    override fun setDataBindingVariables(binding: ViewDataBinding) {
        binding.setVariable(BR.anime, anime)
    }
}

@EpoxyModelClass(layout = R.layout.recyler_item_genre_anime_detail)
abstract class EpoxyAnimeDetailGenreModel: DataBindingEpoxyModel() {
    @EpoxyAttribute
    lateinit var genre: GenreModel

    override fun setDataBindingVariables(binding: ViewDataBinding) {
        binding.setVariable(BR.genre, genre)
    }
}

@EpoxyModelClass(layout = R.layout.recycler_episode_item)
abstract class EpoxyAnimeDetailEpisodeModel: DataBindingEpoxyModel() {
    @EpoxyAttribute
    lateinit var episode: EpisodeModel

    @EpoxyAttribute
    lateinit var clickCallback: EpisodeClickCallback

    override fun setDataBindingVariables(binding: ViewDataBinding) {
        binding.setVariable(BR.episode, episode)
        binding.setVariable(BR.clickListener, clickCallback)
    }
}