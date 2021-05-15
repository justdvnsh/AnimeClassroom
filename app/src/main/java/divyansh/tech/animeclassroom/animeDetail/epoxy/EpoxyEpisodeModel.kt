package divyansh.tech.animeclassroom.animeDetail.epoxy

import androidx.databinding.ViewDataBinding
import com.airbnb.epoxy.DataBindingEpoxyModel
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import divyansh.tech.animeclassroom.BR
import divyansh.tech.animeclassroom.R
import divyansh.tech.animeclassroom.animeDetail.callbacks.EpisodeItemClickCallback
import divyansh.tech.animeclassroom.common.AnimeClickCallback
import divyansh.tech.animeclassroom.models.home.EpisodeModel

@EpoxyModelClass(layout = R.layout.recycler_episode_item)
abstract class EpoxyEpisodeModel: DataBindingEpoxyModel() {

    @EpoxyAttribute
    lateinit var episode: EpisodeModel

    @EpoxyAttribute
    lateinit var clickCallback: AnimeClickCallback

    override fun setDataBindingVariables(binding: ViewDataBinding) {
        binding.setVariable(BR.episode, episode)
        binding.setVariable(BR.clickListener, clickCallback)
    }
}