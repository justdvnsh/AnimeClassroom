package divyansh.tech.animeclassroom.home

import divyansh.tech.animeclassroom.api.HomeScreenApi
import divyansh.tech.animeclassroom.database.AnimeDao
import divyansh.tech.animeclassroom.database.CacheDao
import divyansh.tech.animeclassroom.utils.HomeTypes
import divyansh.tech.animeclassroom.models.home.AnimeModel
import divyansh.tech.animeclassroom.models.home.OfflineAnimeModel
import javax.inject.Inject

class HomeLocalRepo @Inject constructor(
    private val homeScreenApi: HomeScreenApi,
    private val dao: CacheDao
) {

    suspend fun saveAnimeDataOffline(anime: AnimeModel,category:String) {
        val model = OfflineAnimeModel(
            name = anime.name,
            imageUrl = anime.imageUrl.toString(),
            animeUrl = anime.animeUrl.toString(),
            releaseDate = anime.releaseDate,
            episodeNumber = anime.episodeNumber,
            episodeUrl = anime.episodeUrl,
            genre = anime.genre,
            category = category
        )
        dao.insertAnime(model)
    }

    suspend fun getAllPopularAnime(): List<OfflineAnimeModel> {
        return dao.getAnimeOfCategory(HomeTypes.POPULAR_ANIME.name)
    }

    suspend fun getAllPopularMovies(): List<OfflineAnimeModel> {
        return dao.getAnimeOfCategory(HomeTypes.POPULAR_MOVIES.name)
    }

    suspend fun getAllNewSeasons(): List<OfflineAnimeModel> {
        return dao.getAnimeOfCategory(HomeTypes.NEW_SEASON.name)
    }

    suspend fun getAllRecentReleases(): List<OfflineAnimeModel> {
       return dao.getAnimeOfCategory(HomeTypes.RECENT_RELEASE.name)
    }

}