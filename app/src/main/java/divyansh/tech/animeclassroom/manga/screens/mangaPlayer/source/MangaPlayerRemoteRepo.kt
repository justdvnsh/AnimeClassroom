package divyansh.tech.animeclassroom.manga.screens.mangaPlayer.source

import divyansh.tech.animeclassroom.common.utils.C
import divyansh.tech.animeclassroom.common.utils.ResultWrapper
import divyansh.tech.animeclassroom.common.data.mangaApi.MangaHomeApi
import divyansh.tech.animeclassroom.common.utils.MangaParser
import javax.inject.Inject

class MangaPlayerRemoteRepo @Inject constructor(
    private val service: MangaHomeApi
) {

    suspend fun getChapterItems(url: String): ResultWrapper<ArrayList<String>> {
        val response = service.getMangaChapter(C.MANGA_URL + url)
        return MangaParser.parseChapterItems(response.string())
    }
}