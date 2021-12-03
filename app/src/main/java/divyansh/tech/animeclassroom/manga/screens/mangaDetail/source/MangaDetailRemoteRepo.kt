package divyansh.tech.animeclassroom.manga.screens.mangaDetail.source

import divyansh.tech.animeclassroom.common.utils.C
import divyansh.tech.animeclassroom.common.utils.ResultWrapper
import divyansh.tech.animeclassroom.common.data.mangaApi.MangaHomeApi
import divyansh.tech.animeclassroom.common.data.mangaModels.MangaDetail
import divyansh.tech.animeclassroom.common.utils.MangaParser
import javax.inject.Inject

class MangaDetailRemoteRepo @Inject constructor(
    private val service: MangaHomeApi
) {

    suspend fun getMangaDetail(url: String): ResultWrapper<MangaDetail> {
        val response = service.getMangaDetail(C.MANGA_URL + url)
        return MangaParser.parseMangaDetails(response.string())
    }
}