package divyansh.tech.animeclassroom.mangaApi

import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Url

/*\
* Get the interface functions for the home page
* */
interface MangaHomeApi {

    /*
    * Fethces the home page
    * */
    @GET
    suspend fun fetchHomePage(
        @Url url: String
    ) : ResponseBody

    /*
    * Fecthes manga detail
    * */
    @GET
    suspend fun getMangaDetail(
        @Url url: String
    ): ResponseBody

    /*
    * Fetches manga images for a chapter
    * */
    @GET
    suspend fun getMangaChapter(
        @Url url: String
    ): ResponseBody
}