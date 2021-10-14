package divyansh.tech.animeclassroom.animeSearch

import android.util.Log
import divyansh.tech.animeclassroom.C
import divyansh.tech.animeclassroom.ResultWrapper
import divyansh.tech.animeclassroom.api.AnimeSearchApi
import divyansh.tech.animeclassroom.mangaModels.Manga
import divyansh.tech.animeclassroom.models.home.AnimeModel
import divyansh.tech.animeclassroom.utils.MangaParser
import divyansh.tech.animeclassroom.utils.Parser.parsePopularAnimeJson
import javax.inject.Inject

class AnimeSearchRepo @Inject constructor(
    private val animeSearchApi: AnimeSearchApi
) {

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
}