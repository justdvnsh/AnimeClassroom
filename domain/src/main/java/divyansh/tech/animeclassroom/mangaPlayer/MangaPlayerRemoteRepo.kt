package divyansh.tech.animeclassroom.mangaPlayer

import divyansh.tech.animeclassroom.C
import divyansh.tech.animeclassroom.ResultWrapper
import divyansh.tech.animeclassroom.mangaApi.MangaHomeApi
import divyansh.tech.animeclassroom.mangaModels.MangaDetail
import divyansh.tech.animeclassroom.utils.MangaParser
import javax.inject.Inject

class MangaPlayerRemoteRepo @Inject constructor(
    private val service: MangaHomeApi
) {

    suspend fun getChapterItems(url: String): ResultWrapper<ArrayList<String>> {
        val response = service.getMangaChapter(C.MANGA_URL + url)
        return MangaParser.parseChapterItems(response.string())
    }
}