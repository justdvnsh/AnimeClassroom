package divyansh.tech.animeclassroom.animeDetail.epoxy

import androidx.databinding.ViewDataBinding
import androidx.databinding.library.baseAdapters.BR
import com.airbnb.epoxy.DataBindingEpoxyModel
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import divyansh.tech.animeclassroom.R
import divyansh.tech.animeclassroom.models.home.AnimeDetailModel
import divyansh.tech.animeclassroom.models.home.EpisodeModel

@EpoxyModelClass(layout = R.layout.fragment_anime_details_header)
abstract class EpoxyAnimeDetailHeaderModel: DataBindingEpoxyModel() {
    @EpoxyAttribute
    lateinit var anime: AnimeDetailModel

    override fun setDataBindingVariables(binding: ViewDataBinding) {
        binding.setVariable(BR.anime, anime)
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

@EpoxyModelClass(layout = R.layout.recycler_episode_item)
abstract class EpoxyAnimeDetailEpisodeModel: DataBindingEpoxyModel() {
    @EpoxyAttribute
    lateinit var episode: EpisodeModel

    override fun setDataBindingVariables(binding: ViewDataBinding) {
        binding.setVariable(BR.episode, episode)
    }
}