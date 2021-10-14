package divyansh.tech.animeclassroom.api

import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Url

interface GenreSearchApi {

    @GET("/genre/{genre}")
    suspend fun getGenreAnime(
        @Path("genre") genre: String
    ): ResponseBody

    @GET
    suspend fun getGenreManga(
        @Url url: String
    ): ResponseBody
}