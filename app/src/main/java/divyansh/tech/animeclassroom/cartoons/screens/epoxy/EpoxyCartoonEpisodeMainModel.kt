package divyansh.tech.animeclassroom.cartoons.screens.epoxy

import androidx.databinding.ViewDataBinding
import com.airbnb.epoxy.DataBindingEpoxyModel
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.databinding.BR
import divyansh.tech.animeclassroom.R
import divyansh.tech.animeclassroom.common.data.Cartoons
import divyansh.tech.animeclassroom.cartoons.screens.callback.EpisodeClickCallback

@EpoxyModelClass(layout = R.layout.recycler_cartoon_episode_view)
abstract class EpoxyCartoonEpisodeModel(): DataBindingEpoxyModel() {
    @EpoxyAttribute
    lateinit var cartoons: Cartoons

    @EpoxyAttribute
    lateinit var episodeClick: EpisodeClickCallback

    override fun setDataBindingVariables(binding: ViewDataBinding) {
        binding.setVariable(BR.cartoon, cartoons)
        binding.setVariable(BR.cartoonEpisodeClickCallback, episodeClick)
    }
}