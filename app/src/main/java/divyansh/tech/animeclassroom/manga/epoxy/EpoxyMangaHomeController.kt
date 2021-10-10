package divyansh.tech.animeclassroom.manga.epoxy

import com.airbnb.epoxy.TypedEpoxyController
import divyansh.tech.animeclassroom.manga.callbacks.MangaClickCallback
import divyansh.tech.animeclassroom.mangaModels.Manga

class EpoxyMangaHomeController(
    private val mangaClickCallback: MangaClickCallback
): TypedEpoxyController<ArrayList<Manga>>() {
    override fun buildModels(data: ArrayList<Manga>?) {
        data?.let {
            it.forEach {
                epoxyManga {
                    id(it.imageUrl)
                    manga(it)
                    clickCallback(mangaClickCallback)
                    spanSizeOverride { totalSpanCount, _, _ -> totalSpanCount / 2 }
                }
            }
        }
    }
}