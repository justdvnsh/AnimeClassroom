package divyansh.tech.animeclassroom.api

import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Url

/*
* Interface for the Anime Detail Screen
* */
interface AnimeDetailScreenApi {

    /*
    * Get the Details for a particular anime
    * */
    @GET
    suspend fun getAnimeDetails(
        @Url url: String
    ): ResponseBody
}