package divyansh.tech.animeclassroom.manga.screens.mangaDetail.epoxy

import com.airbnb.epoxy.Carousel
import com.airbnb.epoxy.CarouselModel_
import com.airbnb.epoxy.TypedEpoxyController
import divyansh.tech.animeclassroom.animeDetail.epoxy.EpoxyAnimeDetailGenreModel_
import divyansh.tech.animeclassroom.animeDetail.epoxy.epoxyAnimeDetailPlotSummary
import divyansh.tech.animeclassroom.home.epoxy.epoxyTitle
import divyansh.tech.animeclassroom.mangaModels.MangaDetail

class EpoxyMangaDetailController: TypedEpoxyController<MangaDetail>() {
    override fun buildModels(data: MangaDetail?) {
    data?.let {
            epoxyMangaDetailHeader{
                id(it.imageUrl)
                manga(it)
                spanSizeOverride { totalSpanCount, position, itemCount -> totalSpanCount }
            }

            epoxyMangaDetailPlotSummary {
                id(it.name)
                mangaDetail(it)
                spanSizeOverride { totalSpanCount, position, itemCount ->  totalSpanCount}
            }

            epoxyTitle {
                id(it.name)
                headerTitle("Genres")
            }

            val list: ArrayList<EpoxyAnimeDetailGenreModel_> = ArrayList()

            it.genreModel.forEach {
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
                headerTitle("Chapters")
                spanSizeOverride { totalSpanCount, _, _ -> totalSpanCount }
            }

            it.chapters.forEach {
                epoxyMangaDetailChapter {
                    id(it.chapterUrl)
                    chapter(it)
                    spanSizeOverride { totalSpanCount, _, _ ->  totalSpanCount / 2}
                }
            }
        }
    }
}