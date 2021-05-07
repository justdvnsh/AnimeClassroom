package divyansh.tech.animeclassroom.home.epoxy

import com.airbnb.epoxy.Carousel
import com.airbnb.epoxy.CarouselModel_
import com.airbnb.epoxy.TypedEpoxyController
import divyansh.tech.animeclassroom.home.dataModels.*
import divyansh.tech.animeclassroom.home.utils.HomeTypes
import divyansh.tech.animeclassroom.models.home.AnimeModel

class EpoxyHomeController(): TypedEpoxyController<List<HomeMainModel>>() {
    override fun buildModels(data: List<HomeMainModel>?) {
        data?.let {
            data.forEach {
                when (it.typeValue) {
                    HomeTypes.RECENT_RELEASE -> addRecentSubs("Recent Subs", it.animeList)
                    HomeTypes.POPULAR_ANIME -> addAnime("Popular Anime", it.animeList)
                    HomeTypes.NEW_SEASON -> addAnime("New Seasons", it.animeList)
                    HomeTypes.POPULAR_MOVIES -> addAnime("Popular Movies", it.animeList)
                }
            }
        }
    }

    private fun addRecentSubs(title: String, data: List<AnimeModel>?) {
        data?.let {
            epoxyTitle {
                id(title)
                headerTitle(title)
            }

            val list: ArrayList<EpoxyAnimeMetaModels_> = ArrayList()

            data.forEach {
                list.add(
                    EpoxyAnimeMetaModels_()
                        .id(it.animeUrl)
                        .anime(it)
                )
            }

            CarouselModel_()
                .id("RECENT_RELEASE")
                .models(list)
                .padding(Carousel.Padding.dp(20,0,20,0,20))
                .addTo(this)
        }
    }

    private fun addAnime(title: String, data: List<AnimeModel>?) {
        data?.let {
            epoxyTitle {
                id(title)
                headerTitle(title)
            }

            val list: ArrayList<EpoxyAnimeModels_> = ArrayList()

            data.forEach {
                list.add(
                    EpoxyAnimeModels_()
                        .id(it.animeUrl)
                        .anime(it)
                )
            }

            CarouselModel_()
                .id(title)
                .models(list)
                .padding(Carousel.Padding.dp(20,0,20,0,20))
                .addTo(this)
        }
    }
}