package divyansh.tech.animeclassroom.common.data.api

import okhttp3.Response
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

/*
* Interface to Search Animes
* */
interface AnimeSearchApi {

    /*
    * Function to search for animes
    * */
    @GET("/search.html")
    suspend fun searchAnime(
            @Query("keyword") keyword: String
    ): ResponseBody

    @GET
    suspend fun searchManga(
        @Url url: String
    ): ResponseBody
}