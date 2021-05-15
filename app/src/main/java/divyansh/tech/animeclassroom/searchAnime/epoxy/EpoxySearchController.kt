package divyansh.tech.animeclassroom.searchAnime.epoxy

import com.airbnb.epoxy.TypedEpoxyController
import divyansh.tech.animeclassroom.common.AnimeClickCallback
import divyansh.tech.animeclassroom.home.callbacks.HomeScreenCallbacks
import divyansh.tech.animeclassroom.home.epoxy.epoxyAnimeModels
import divyansh.tech.animeclassroom.models.home.AnimeModel

class EpoxySearchController(
        private val animeClickCallback: AnimeClickCallback
): TypedEpoxyController<ArrayList<AnimeModel>>() {
    override fun buildModels(data: ArrayList<AnimeModel>?) {
        data?.let {
            it.forEach {
                epoxyAnimeModels {
                    id(it.imageUrl)
                    anime(it)
                    callback(animeClickCallback)
                }
            }
        }
    }
}