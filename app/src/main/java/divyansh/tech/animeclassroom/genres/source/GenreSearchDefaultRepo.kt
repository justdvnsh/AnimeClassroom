package divyansh.tech.animeclassroom.genres.source

import divyansh.tech.animeclassroom.common.utils.ResultWrapper
import divyansh.tech.animeclassroom.common.data.Manga
import divyansh.tech.animeclassroom.common.data.AnimeModel
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