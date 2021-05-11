package divyansh.tech.animeclassroom.animeDetail.epoxy

import com.airbnb.epoxy.TypedEpoxyController
import divyansh.tech.animeclassroom.animeDetail.callbacks.EpisodeItemClickCallback
import divyansh.tech.animeclassroom.models.home.EpisodeModel

class EpoxyEpisodeController (
    private val clickCallback: EpisodeItemClickCallback
): TypedEpoxyController<List<EpisodeModel>>() {
    override fun buildModels(data: List<EpisodeModel>?) {
        data?.let {
            it.forEach {
                epoxyEpisode {
                    id(it.episodeUrl)
                    episode(it)
                    clickCallback(clickCallback)
                }
            }
        }
    }
}