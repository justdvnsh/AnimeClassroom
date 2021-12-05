package divyansh.tech.animeclassroom.player.epoxy

import com.airbnb.epoxy.TypedEpoxyController
import divyansh.tech.animeclassroom.animeDetail.callbacks.EpisodeClickCallback
import divyansh.tech.animeclassroom.animeDetail.epoxy.epoxyAnimeDetailEpisode
import divyansh.tech.animeclassroom.common.AnimeClickCallback
import divyansh.tech.animeclassroom.common.data.PlayerScreenModel

class EpoxyPlayerController(
    private val callback: AnimeClickCallback,
    private val imageUrl: String
): TypedEpoxyController<PlayerScreenModel>() {
    override fun buildModels(data: PlayerScreenModel?) {
        data?.let {model ->
            model.episodeList.forEach {
                epoxyPlayerEpisode {
                    id(it.hashCode())
                    episode(it)
                    imageUrl(imageUrl)
                    clickCallback(callback)
                    activeEpisode(model.activeEpisode.toString())
                }
            }
        }
    }
}