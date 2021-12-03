package divyansh.tech.animeclassroom.genres.source

import divyansh.tech.animeclassroom.common.utils.C
import divyansh.tech.animeclassroom.common.utils.ResultWrapper
import divyansh.tech.animeclassroom.common.data.api.GenreSearchApi
import divyansh.tech.animeclassroom.common.data.mangaModels.Manga
import divyansh.tech.animeclassroom.common.data.home.AnimeModel
import divyansh.tech.animeclassroom.common.utils.MangaParser
import divyansh.tech.animeclassroom.common.utils.Parser
import javax.inject.Inject

class GenreSearchRemoteRepo @Inject constructor(
    private val genreSearchApi: GenreSearchApi
) {

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
}