package divyansh.tech.animeclassroom.manga.screens.mangaPlayer.source

import divyansh.tech.animeclassroom.common.utils.C
import divyansh.tech.animeclassroom.common.utils.ResultWrapper
import divyansh.tech.animeclassroom.common.utils.MangaParser
import okhttp3.ResponseBody
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Url
import javax.inject.Inject

class MangaPlayerRemoteRepo @Inject constructor(
    retrofit: Retrofit
) {

    private val service = retrofit.create(MangaChapterApi::class.java)

    suspend fun getChapterItems(url: String): ResultWrapper<ArrayList<String>> {
        val response = service.getMangaChapter(C.MANGA_URL + url)
        return MangaParser.parseChapterItems(response.string())
    }

    interface MangaChapterApi {

        /*
        * Fetches manga images for a chapter
        * */
        @GET
        suspend fun getMangaChapter(
            @Url url: String
        ): ResponseBody
    }
}