package divyansh.tech.animeclassroom.cartoons.screens.epoxy

import com.airbnb.epoxy.TypedEpoxyController
import divyansh.tech.animeclassroom.common.data.cartoonModels.Cartoons
import divyansh.tech.animeclassroom.cartoons.screens.callback.EpisodeClickCallback

class EpoxyCartoonEpisodeController(
    private val callback: EpisodeClickCallback
): TypedEpoxyController<ArrayList<Cartoons>>() {
    override fun buildModels(data: ArrayList<Cartoons>?) {
        data?.let {
            it.forEach {
                epoxyCartoonEpisode {
                    id(it.hashCode())
                    cartoons(it)
                    episodeClick(callback)
                    spanSizeOverride { totalSpanCount, _, _ ->  totalSpanCount / 2}
                }
            }
        }
    }
}