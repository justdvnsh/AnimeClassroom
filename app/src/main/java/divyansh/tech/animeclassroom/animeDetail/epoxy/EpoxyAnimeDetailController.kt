package divyansh.tech.animeclassroom.animeDetail.epoxy

import com.airbnb.epoxy.Carousel
import com.airbnb.epoxy.CarouselModel_
import com.airbnb.epoxy.Typed2EpoxyController
import divyansh.tech.animeclassroom.home.epoxy.EpoxyGenreModels_
import divyansh.tech.animeclassroom.home.epoxy.epoxyGenreModels
import divyansh.tech.animeclassroom.home.epoxy.epoxyTitle
import divyansh.tech.animeclassroom.models.home.AnimeDetailModel
import divyansh.tech.animeclassroom.models.home.EpisodeModel
import divyansh.tech.animeclassroom.models.home.GenreModel

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

            epoxyTitle {
                id(it.name)
                headerTitle("Genres")
            }

            val list: ArrayList<EpoxyAnimeDetailGenreModel_> = ArrayList()

            it.genre.forEach {
                list.add(
                    EpoxyAnimeDetailGenreModel_()
                        .id(it.genreUrl)
                        .genre(it)
                )
            }

            CarouselModel_()
                .id(it.hashCode())
                .models(list)
                .padding(Carousel.Padding.dp(20,0,20,0,20))
                .addTo(this)

            epoxyTitle {
                id(it.name)
                headerTitle("Episodes")
            }

            data2?.let {
                val episodelist: ArrayList<EpoxyAnimeDetailEpisodeModel_> = ArrayList()

                it.forEach {
                    episodelist.add(
                        EpoxyAnimeDetailEpisodeModel_()
                            .id(it.episodeUrl)
                            .episode(it)
                    )
                }

                CarouselModel_()
                    .id(it.hashCode())
                    .models(episodelist)
                    .padding(Carousel.Padding.dp(20,0,20,0,20))
                    .addTo(this)
            }
        }
    }

}