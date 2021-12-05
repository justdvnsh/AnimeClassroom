package divyansh.tech.animeclassroom.home.epoxy

import com.airbnb.epoxy.Carousel
import com.airbnb.epoxy.CarouselModel_
import com.airbnb.epoxy.TypedEpoxyController
import com.airbnb.epoxy.group
import divyansh.tech.animeclassroom.R
import divyansh.tech.animeclassroom.common.AnimeClickCallback
import divyansh.tech.animeclassroom.home.dataModels.*
import divyansh.tech.animeclassroom.common.utils.HomeTypes
import divyansh.tech.animeclassroom.common.data.AnimeModel
import divyansh.tech.animeclassroom.common.data.GenreModel

class EpoxyHomeController(
        private val callback: AnimeClickCallback
): TypedEpoxyController<List<HomeMainModel>>() {
    override fun buildModels(data: List<HomeMainModel>?) {
        data?.let {
            data.forEach {
                when (it.type) {
                    HomeTypes.RECENT_RELEASE -> addRecentSubs("Recent Subs", it.feedResult)
                    HomeTypes.POPULAR_ANIME -> addAnime("Popular Anime", it.feedResult)
                    HomeTypes.NEW_SEASON -> addAnime("New Seasons", it.feedResult)
                    HomeTypes.POPULAR_MOVIES -> addAnime("Popular Movies", it.feedResult)
                    HomeTypes.GENRES -> addGenres("Genres", it.feedResult)
                }
            }
        }
    }

    private fun addRecentSubs(title: String, data: ArrayList<*>) {
        data.let {
            epoxyTitle {
                id(title)
                headerTitle(title)
            }

            val list: ArrayList<EpoxyAnimeMetaModels_> = ArrayList()

            data.forEach {
                list.add(
                        EpoxyAnimeMetaModels_()
                                .id((it as AnimeModel).episodeUrl)
                                .anime(it)
                                .callback(callback)
                )
            }

            CarouselModel_()
                    .id("RECENT_RELEASE")
                    .models(list)
                    .padding(Carousel.Padding.dp(20,0,20,0,20))
                    .addTo(this)
        }
    }

    private fun addAnime(title: String, data: ArrayList<*>) {
        data.let {
            epoxyTitle {
                id(title)
                headerTitle(title)
            }

            val list: ArrayList<EpoxyAnimeModels_> = ArrayList()

            data.forEach {
                list.add(
                        EpoxyAnimeModels_()
                                .id((it as AnimeModel).animeUrl)
                                .anime(it)
                                .callback(callback)
                )
            }


            CarouselModel_()
                    .id(title)
                    .models(list)
                    .padding(Carousel.Padding.dp(20,0,20,0,20))
                    .addTo(this)
        }
    }

    private fun addGenres(title: String, data: ArrayList<*>) {
        data.let {
            epoxyTitle {
                id(title)
                headerTitle(title)
            }

            val list: ArrayList<EpoxyGenreModels_> = ArrayList()

            data.forEach {
                list.add(
                    EpoxyGenreModels_()
                            .id((it as GenreModel).genreUrl)
                            .genre(it)
                            .callback(callback)
                )
            }

            group {
                id("character_group")
                layout(R.layout.recycler_category_background)
                add(
                    CategoryBackgroundModel_()
                        .id(list.hashCode())
                        .models(list)
                        .padding(Carousel.Padding.dp(16, 18, 16, 0, 8))
                )
            }
        }
    }
}