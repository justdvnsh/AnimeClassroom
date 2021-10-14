package divyansh.tech.animeclassroom.genreSearch

import divyansh.tech.animeclassroom.ResultWrapper
import divyansh.tech.animeclassroom.mangaModels.Manga
import divyansh.tech.animeclassroom.models.home.AnimeModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GenreSearchDefaultRepo @Inject constructor(
    private val remoteRepo: GenreSearchRemoteRepo
){
    suspend fun genreSearchAnime(genre: String): ResultWrapper<ArrayList<AnimeModel>> {
        return remoteRepo.getGenreAnime(genre)
    }

    suspend fun genreSearchManga(genre: String): ResultWrapper<ArrayList<Manga>> {
        return remoteRepo.getGenreManga(genre)
    }
}