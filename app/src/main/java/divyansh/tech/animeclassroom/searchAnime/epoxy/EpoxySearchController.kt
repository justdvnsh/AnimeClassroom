package divyansh.tech.animeclassroom.searchAnime.epoxy

import com.airbnb.epoxy.Carousel
import com.airbnb.epoxy.CarouselModel_
import com.airbnb.epoxy.TypedEpoxyController
import divyansh.tech.animeclassroom.common.AnimeClickCallback
import divyansh.tech.animeclassroom.common.SearchScreenDataModel
import divyansh.tech.animeclassroom.home.epoxy.epoxyAnimeModels
import divyansh.tech.animeclassroom.home.epoxy.epoxyTitle
import divyansh.tech.animeclassroom.manga.epoxy.EpoxyMangaModel_

class EpoxySearchController(
        private val callback: AnimeClickCallback
): TypedEpoxyController<SearchScreenDataModel>() {
    override fun buildModels(data: SearchScreenDataModel?) {
        data?.let {
            if (it.mangas.isNotEmpty() && it.mangas.size != 1) {
                epoxyTitle {
                    id("GENRETITLE")
                    headerTitle("Mangas")
                    spanSizeOverride { totalSpanCount, _, _ -> totalSpanCount }
                }

                val list: ArrayList<EpoxyMangaModel_> = ArrayList()

                it.mangas.forEach {
                    list.add(
                        EpoxyMangaModel_()
                            .id(it.imageUrl)
                            .manga(it)
                            .clickCallback(callback)
                    )
                }

                CarouselModel_()
                    .id("RECENT_RELEASE")
                    .models(list)
                    .padding(Carousel.Padding.dp(20,0,20,0,20))
                    .addTo(this)
            }

            if (it.animes.isNotEmpty() && it.animes.size != 1) {
                epoxyTitle {
                    id("ANIMETITLE")
                    headerTitle("Animes")
                    spanSizeOverride { totalSpanCount, _, _ -> totalSpanCount }
                }

                it.animes.forEach {
                    epoxyAnimeModels {
                        id(it.imageUrl)
                        anime(it)
                        callback(callback)
                    }
                }
            }
        }
    }
}