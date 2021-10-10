package divyansh.tech.animeclassroom.favorites

import divyansh.tech.animeclassroom.models.home.AnimeDetailModel
import javax.inject.Inject

class FavoriteAnimeDefaultRepo @Inject constructor(
    private val repo: FavoriteAnimeLocalRepo
) {

    suspend fun getAllAnimes() = repo.getAllAnime()
    suspend fun saveAnime(animeDetailModel: AnimeDetailModel, animeUrl: String) = repo.saveAnime(animeDetailModel, animeUrl)
//    suspend fun deleteAnime(animeDetailModel: AnimeDetailModel) = repo.deleteAnime(animeDetailModel)
}