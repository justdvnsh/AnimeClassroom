package divyansh.tech.animeclassroom.favorites.screens.animes.epoxy

import com.airbnb.epoxy.TypedEpoxyController
import divyansh.tech.animeclassroom.common.AnimeClickCallback
import divyansh.tech.animeclassroom.common.data.OfflineAnimeModel

class EpoxyFavoriteAnimeController(
    private val animeClickCallback: AnimeClickCallback
) : TypedEpoxyController<List<OfflineAnimeModel>>() {

    override fun buildModels(data: List<OfflineAnimeModel>?) {
        data?.let { offlineAnimeModels ->
            offlineAnimeModels.forEach {
                epoxyFavoriteAnime {
                    id(it.animeUrl)
                    anime(it)
                    animeCallback(animeClickCallback)
                }
            }
        }
    }
}