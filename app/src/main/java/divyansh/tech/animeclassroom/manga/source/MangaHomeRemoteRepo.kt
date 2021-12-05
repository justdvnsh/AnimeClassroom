package divyansh.tech.animeclassroom.manga.source

import android.util.Log
import divyansh.tech.animeclassroom.common.utils.ResultWrapper
import divyansh.tech.animeclassroom.common.utils.MangaParser.parseFeaturedTitles
import divyansh.tech.animeclassroom.common.utils.MangaParser.parseHomePageData
import okhttp3.ResponseBody
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Url
import javax.inject.Inject

class MangaHomeRemoteRepo @Inject constructor(
    retrofit: Retrofit
){

    private val mangaHomeApi = retrofit.create(MangaHomeApi::class.java)

    /*
    * get the home page data
    * */
    suspend fun getHomePage(url: String): ResultWrapper<*> {
        Log.i("MANGA", "INSIDE REMOTE REPO")
        return parseHomePageData(
            mangaHomeApi.fetchHomePage(url).string()
        )
    }

    /*
    * get the featured titles data
    * */
    suspend fun getFeaturedTitles(url: String): ResultWrapper<*> {
        return parseFeaturedTitles(
            mangaHomeApi.fetchHomePage(url).string()
        )
    }

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
}