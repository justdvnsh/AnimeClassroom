package divyansh.tech.animeclassroom.animeDetail.epoxy

import com.airbnb.epoxy.Carousel
import com.airbnb.epoxy.CarouselModel_
import com.airbnb.epoxy.Typed2EpoxyController
import divyansh.tech.animeclassroom.animeDetail.callbacks.EpisodeClickCallback
import divyansh.tech.animeclassroom.animeDetail.callbacks.FavoriteClickCallback
import divyansh.tech.animeclassroom.home.epoxy.epoxyTitle
import divyansh.tech.animeclassroom.common.data.AnimeDetailModel
import divyansh.tech.animeclassroom.common.data.EpisodeModel

class EpoxyAnimeDetailController(
    private val clickCallback: EpisodeClickCallback,
    private val favoriteClickCallback: FavoriteClickCallback,
    private val animeUrl: String
): Typed2EpoxyController<AnimeDetailModel, List<EpisodeModel>>() {
    override fun buildModels(data1: AnimeDetailModel?, data2: List<EpisodeModel>?) {
        data1?.let {anime->
            epoxyAnimeDetailHeader {
                id(anime.imageUrl)
                anime(anime)
                favoriteClickCallback(favoriteClickCallback)
                animeUrl(animeUrl)
                spanSizeOverride { totalSpanCount, position, itemCount -> totalSpanCount }
            }

            epoxyAnimeDetailPlotSummary {
                id(anime.name)
                anime(anime)
                spanSizeOverride { totalSpanCount, position, itemCount ->  totalSpanCount}
            }

            epoxyTitle {
                id(anime.name)
                headerTitle("Genres")
            }

            val list: ArrayList<EpoxyAnimeDetailGenreModel_> = ArrayList()

            anime.genre.forEach {
                list.add(
                    EpoxyAnimeDetailGenreModel_()
                        .id(it.genreUrl)
                        .genre(it)
                        .callback(clickCallback)
                )
            }

            CarouselModel_()
                .id(anime.hashCode())
                .models(list)
                .padding(Carousel.Padding.dp(20,0,20,0,20))
                .addTo(this)

            epoxyTitle {
                id(anime.name)
                headerTitle("Episodes")
                spanSizeOverride { totalSpanCount, _, _ -> totalSpanCount }
            }

            data2?.let {
//                val episodelist: ArrayList<EpoxyAnimeDetailEpisodeModel_> = ArrayList()

                it.forEach {
//                    EpoxyAnimeDetailEpisodeModel_()
//                        .id(it.episodeUrl)
//                        .episode(it)
//                        .clickCallback(clickCallback)
                    epoxyAnimeDetailEpisode {
                        id(it.episodeUrl)
                        episode(it)
                        clickCallback(clickCallback)
                        imageUrl(anime.imageUrl)
                    }
                }

//                group {
//                    id("episode_group")
//                    layout(R.layout.recycler_category_background)
//                    add(
//                        CategoryBackgroundModel_()
//                            .id(list.hashCode())
//                            .models(episodelist)
//                            .padding(Carousel.Padding.dp(16, 18, 16, 0, 8))
//                    )
//                }
            }
        }
    }

}