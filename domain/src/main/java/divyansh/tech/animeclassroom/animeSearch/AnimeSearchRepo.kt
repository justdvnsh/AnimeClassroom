package divyansh.tech.animeclassroom.animeSearch

import android.util.Log
import divyansh.tech.animeclassroom.ResultWrapper
import divyansh.tech.animeclassroom.api.AnimeSearchApi
import divyansh.tech.animeclassroom.utils.Parser.parsePopularAnimeJson
import javax.inject.Inject

class AnimeSearchRepo @Inject constructor(
    private val animeSearchApi: AnimeSearchApi
) {

    /*
    * Function to get the Animes
    * */
    suspend fun searchAnimes(url: String): ResultWrapper<*> {
        val response = animeSearchApi.searchAnime(url).string()
        Log.i("Search-url", url)
        return parsePopularAnimeJson(response)
    }
}