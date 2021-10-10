package divyansh.tech.animeclassroom.manga.epoxy

import com.airbnb.epoxy.TypedEpoxyController
import divyansh.tech.animeclassroom.mangaModels.Manga

class EpoxyMangaHomeController(): TypedEpoxyController<ArrayList<Manga>>() {
    override fun buildModels(data: ArrayList<Manga>?) {
        data?.let {
            it.forEach {
                epoxyManga {
                    id(it.imageUrl)
                    manga(it)
                    spanSizeOverride { totalSpanCount, _, _ -> totalSpanCount / 2 }
                }
            }
        }
    }
}