package divyansh.tech.animeclassroom.searchAnime.source

import android.util.Log
import divyansh.tech.animeclassroom.common.utils.C
import divyansh.tech.animeclassroom.common.utils.ResultWrapper
import divyansh.tech.animeclassroom.common.data.Manga
import divyansh.tech.animeclassroom.common.data.AnimeModel
import divyansh.tech.animeclassroom.common.utils.MangaParser
import divyansh.tech.animeclassroom.common.utils.Parser.parsePopularAnimeJson
import okhttp3.ResponseBody
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url
import javax.inject.Inject

class AnimeSearchRepo @Inject constructor(
    retrofit: Retrofit
) {

    private val animeSearchApi = retrofit.create(AnimeSearchApi::class.java)

    /*
    * Function to get the Animes
    * */
    suspend fun searchAnimes(url: String): ResultWrapper<ArrayList<AnimeModel>> {
        val response = animeSearchApi.searchAnime(url).string()
        Log.i("Search-url", url)
        return parsePopularAnimeJson(response)
    }

    suspend fun searchManga(keyword: String): ResultWrapper<ArrayList<Manga>> {
        val response = animeSearchApi.searchManga(C.MANGA_URL + "search?type=titles&title=${keyword}")
        return MangaParser.getMangaSearch(response.string())
    }

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
}