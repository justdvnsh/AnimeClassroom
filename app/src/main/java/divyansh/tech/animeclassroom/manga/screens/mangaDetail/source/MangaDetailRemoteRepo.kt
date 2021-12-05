package divyansh.tech.animeclassroom.manga.screens.mangaDetail.source

import divyansh.tech.animeclassroom.common.utils.C
import divyansh.tech.animeclassroom.common.utils.ResultWrapper
import divyansh.tech.animeclassroom.common.data.MangaDetail
import divyansh.tech.animeclassroom.common.utils.MangaParser
import okhttp3.ResponseBody
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Url
import javax.inject.Inject

class MangaDetailRemoteRepo @Inject constructor(
    retrofit: Retrofit
) {

    private val service = retrofit.create(MangaDetailApi::class.java)

    suspend fun getMangaDetail(url: String): ResultWrapper<MangaDetail> {
        val response = service.getMangaDetail(C.MANGA_URL + url)
        return MangaParser.parseMangaDetails(response.string())
    }

    interface MangaDetailApi {

        /*
        * Fecthes manga detail
        * */
        @GET
        suspend fun getMangaDetail(
            @Url url: String
        ): ResponseBody
    }
}