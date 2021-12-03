package divyansh.tech.animeclassroom.manga.screens.mangaDetail.epoxy

import com.airbnb.epoxy.Carousel
import com.airbnb.epoxy.CarouselModel_
import com.airbnb.epoxy.TypedEpoxyController
import divyansh.tech.animeclassroom.animeDetail.epoxy.EpoxyAnimeDetailGenreModel_
import divyansh.tech.animeclassroom.home.epoxy.epoxyTitle
import divyansh.tech.animeclassroom.manga.screens.mangaDetail.callbacks.MangaDetailCallbacks
import divyansh.tech.animeclassroom.common.data.mangaModels.MangaDetail

class EpoxyMangaDetailController(
    private val callback: MangaDetailCallbacks
): TypedEpoxyController<MangaDetail>() {
    override fun buildModels(data: MangaDetail?) {
    data?.let {manga ->
            epoxyMangaDetailHeader{
                id(manga.imageUrl)
                manga(manga)
                spanSizeOverride { totalSpanCount, position, itemCount -> totalSpanCount }
            }

            epoxyMangaDetailPlotSummary {
                id(manga.name)
                mangaDetail(manga)
                spanSizeOverride { totalSpanCount, position, itemCount ->  totalSpanCount}
            }

            epoxyTitle {
                id(manga.name)
                headerTitle("Genres")
            }

            val list: ArrayList<EpoxyAnimeDetailGenreModel_> = ArrayList()

        manga.genreModel.forEach {
                list.add(
                    EpoxyAnimeDetailGenreModel_()
                        .id(it.genreUrl)
                        .genre(it)
                        .callback(callback)
                )
            }

            CarouselModel_()
                .id(manga.hashCode())
                .models(list)
                .padding(Carousel.Padding.dp(20,0,20,0,20))
                .addTo(this)

            epoxyTitle {
                id(manga.name)
                headerTitle("Chapters")
                spanSizeOverride { totalSpanCount, _, _ -> totalSpanCount }
            }

        manga.chapters.forEach {
                epoxyMangaDetailChapter {
                    id(it.chapterUrl)
                    chapter(it)
                    mangaName(manga.name)
                    chapterClick(callback)
                    spanSizeOverride { totalSpanCount, _, _ ->  totalSpanCount / 2}
                }
            }
        }
    }
}