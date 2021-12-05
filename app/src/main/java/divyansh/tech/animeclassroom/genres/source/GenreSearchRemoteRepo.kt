package divyansh.tech.animeclassroom.genres.source

import divyansh.tech.animeclassroom.common.utils.C
import divyansh.tech.animeclassroom.common.utils.ResultWrapper
import divyansh.tech.animeclassroom.common.data.Manga
import divyansh.tech.animeclassroom.common.data.AnimeModel
import divyansh.tech.animeclassroom.common.utils.MangaParser
import divyansh.tech.animeclassroom.common.utils.Parser
import okhttp3.ResponseBody
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Url
import javax.inject.Inject

class GenreSearchRemoteRepo @Inject constructor(
    retrofit: Retrofit
) {

    private val genreSearchApi = retrofit.create(GenreSearchApi::class.java)

    suspend fun getGenreAnime(genre: String): ResultWrapper<ArrayList<AnimeModel>> {
        return try {
            val response = genreSearchApi.getGenreAnime(genre.trim())
            Parser.parsePopularAnimeJson(response.string())
        } catch (e: Exception) {
            ResultWrapper.Error("Could Not Parse", null)
        }
    }

    suspend fun getGenreManga(genre: String): ResultWrapper<ArrayList<Manga>> {
        return try {
            val response = genreSearchApi.getGenreManga(C.MANGA_URL + "search?genre=${genre.trim()}")
            MangaParser.getMangaSearch(response.string())
        } catch (e: Exception) {
            ResultWrapper.Error("Could Not Parse", null)
        }
    }


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
}