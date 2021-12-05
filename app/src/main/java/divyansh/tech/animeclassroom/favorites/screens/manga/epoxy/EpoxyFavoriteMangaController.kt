package divyansh.tech.animeclassroom.favorites.screens.manga.epoxy

import com.airbnb.epoxy.TypedEpoxyController
import divyansh.tech.animeclassroom.common.AnimeClickCallback
import divyansh.tech.animeclassroom.common.data.Manga

class EpoxyFavoriteMangaController(
    private val mangaClickCallback: AnimeClickCallback
    ): TypedEpoxyController<List<Manga>>() {

    override fun buildModels(data: List<Manga>?) {
        data?.let{
            it.forEach{
                epoxyFavoriteManga {
                    id(it.mangaUrl)
                    manga(it)
                    mangaCallback(mangaClickCallback)
                }
            }
        }
    }
}