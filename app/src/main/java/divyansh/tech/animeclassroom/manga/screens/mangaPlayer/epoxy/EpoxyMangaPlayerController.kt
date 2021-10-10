package divyansh.tech.animeclassroom.manga.screens.mangaPlayer.epoxy

import com.airbnb.epoxy.TypedEpoxyController

class EpoxyMangaPlayerController(): TypedEpoxyController<ArrayList<String>>() {
    override fun buildModels(data: ArrayList<String>?) {
        data?.let {
            it.forEach {
                epoxyMangaPlayer {
                    id(it)
                    imageUrl(it)
                }
            }
        }
    }
}