package divyansh.tech.animeclassroom.mangaDetail

import divyansh.tech.animeclassroom.C
import divyansh.tech.animeclassroom.ResultWrapper
import divyansh.tech.animeclassroom.mangaApi.MangaHomeApi
import divyansh.tech.animeclassroom.mangaModels.MangaDetail
import divyansh.tech.animeclassroom.utils.MangaParser
import javax.inject.Inject

class MangaDetailRemoteRepo @Inject constructor(
    private val service: MangaHomeApi
) {

    suspend fun getMangaDetail(url: String): ResultWrapper<MangaDetail> {
        val response = service.getMangaDetail(C.MANGA_URL + url)
        return MangaParser.parseMangaDetails(response.string())
    }
}