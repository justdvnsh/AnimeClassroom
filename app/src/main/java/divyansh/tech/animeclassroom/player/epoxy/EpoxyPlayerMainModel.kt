package divyansh.tech.animeclassroom.player.epoxy

import androidx.databinding.ViewDataBinding
import androidx.databinding.library.baseAdapters.BR
import com.airbnb.epoxy.DataBindingEpoxyModel
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import divyansh.tech.animeclassroom.R
import divyansh.tech.animeclassroom.common.AnimeClickCallback
import divyansh.tech.animeclassroom.common.data.EpisodeModel

@EpoxyModelClass(layout = R.layout.recycler_episode_item_player)
abstract class EpoxyPlayerEpisodeModel: DataBindingEpoxyModel() {
    @EpoxyAttribute
    lateinit var episode: EpisodeModel

    @EpoxyAttribute
    lateinit var clickCallback: AnimeClickCallback

    @EpoxyAttribute
    lateinit var imageUrl: String

    @EpoxyAttribute
    lateinit var activeEpisode: String

    override fun setDataBindingVariables(binding: ViewDataBinding) {
        binding.setVariable(BR.episode, episode)
        binding.setVariable(BR.clickListener, clickCallback)
        binding.setVariable(BR.imageUrl, imageUrl)
        binding.setVariable(BR.activeEpisode, activeEpisode)
    }
}