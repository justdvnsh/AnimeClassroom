package divyansh.tech.animeclassroom.searchAnime.source

import divyansh.tech.animeclassroom.common.utils.ResultWrapper
import divyansh.tech.animeclassroom.common.data.Manga
import divyansh.tech.animeclassroom.common.data.AnimeModel
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