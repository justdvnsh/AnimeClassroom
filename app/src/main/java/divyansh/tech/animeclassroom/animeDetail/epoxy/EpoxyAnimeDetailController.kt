package divyansh.tech.animeclassroom.animeDetail.epoxy

import com.airbnb.epoxy.Typed2EpoxyController
import divyansh.tech.animeclassroom.models.home.AnimeDetailModel
import divyansh.tech.animeclassroom.models.home.EpisodeModel

class EpoxyAnimeDetailController: Typed2EpoxyController<AnimeDetailModel, List<EpisodeModel>>() {
    override fun buildModels(data1: AnimeDetailModel?, data2: List<EpisodeModel>?) {
        data1?.let {
            epoxyAnimeDetailHeader {
                id(it.imageUrl)
                anime(it)
                spanSizeOverride { totalSpanCount, position, itemCount -> totalSpanCount }
            }

            epoxyAnimeDetailPlotSummary {
                id(it.name)
                anime(it)
                spanSizeOverride { totalSpanCount, position, itemCount ->  totalSpanCount}
            }

            data2?.let {
                it.forEach {
                    epoxyAnimeDetailEpisode {
                        id(it.episodeUrl)
                        episode(it)
                        spanSizeOverride { totalSpanCount, position, itemCount ->  totalSpanCount / 3}
                    }
                }
            }
        }
    }

}