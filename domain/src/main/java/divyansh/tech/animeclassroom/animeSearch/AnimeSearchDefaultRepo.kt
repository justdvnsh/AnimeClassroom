package divyansh.tech.animeclassroom.animeSearch

import divyansh.tech.animeclassroom.ResultWrapper
import divyansh.tech.animeclassroom.mangaModels.Manga
import divyansh.tech.animeclassroom.models.home.AnimeModel
import javax.inject.Inject

class AnimeSearchDefaultRepo @Inject constructor(
    private val remoteRepo: AnimeSearchRepo
){
    suspend fun searchAnime(keyword: String): ResultWrapper<ArrayList<AnimeModel>> {
        return remoteRepo.searchAnimes(keyword)
    }

    suspend fun searchManga(keyword: String): ResultWrapper<ArrayList<Manga>> {
        return remoteRepo.searchManga(keyword)
    }
}