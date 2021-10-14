package divyansh.tech.animeclassroom.genreSearch

import android.util.Log
import divyansh.tech.animeclassroom.C
import divyansh.tech.animeclassroom.ResultWrapper
import divyansh.tech.animeclassroom.api.GenreSearchApi
import divyansh.tech.animeclassroom.mangaModels.Manga
import divyansh.tech.animeclassroom.models.home.AnimeModel
import divyansh.tech.animeclassroom.utils.MangaParser
import divyansh.tech.animeclassroom.utils.Parser
import javax.inject.Inject

class GenreSearchRemoteRepo @Inject constructor(
    private val genreSearchApi: GenreSearchApi
) {

    suspend fun getGenreAnime(genre: String): ResultWrapper<ArrayList<AnimeModel>> {
        val response = genreSearchApi.getGenreAnime(genre.trim())
        return Parser.parsePopularAnimeJson(response.string())
    }

    suspend fun getGenreManga(genre: String): ResultWrapper<ArrayList<Manga>> {
        Log.i("REQ MANGA SEARCH-> ", C.MANGA_URL + "search?genre=${genre.trim()}")
        val response = genreSearchApi.getGenreManga(C.MANGA_URL + "search?genre=${genre.trim()}")
        return MangaParser.getMangaSearch(response.string())
    }
}