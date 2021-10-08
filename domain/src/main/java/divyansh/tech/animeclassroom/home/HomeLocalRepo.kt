package divyansh.tech.animeclassroom.home

import android.util.Log
import divyansh.tech.animeclassroom.api.HomeScreenApi
import divyansh.tech.animeclassroom.database.AnimeDao
import divyansh.tech.animeclassroom.models.home.AnimeModel
import divyansh.tech.animeclassroom.models.home.OfflineAnimeModel
import javax.inject.Inject

class HomeLocalRepo @Inject constructor(
    private val homeScreenApi: HomeScreenApi,
    private val dao: AnimeDao
) {

    suspend fun saveAnimeDataOffline(anime: AnimeModel,category:String) {
        val model = OfflineAnimeModel(
            name = anime.name,
            imageUrl = anime.imageUrl.toString(),
            animeUrl = anime.animeUrl.toString(),
            category = category
        )
        dao.insertAnime(model)
    }

    suspend fun getAllPopularAnime(){
        // added logs for testing purpose
        Log.d("Localdb", dao.getAnimeOfCategory(POPULAR_ANIME)[0].category)
        Log.d("Localdb", dao.getAnimeOfCategory(POPULAR_ANIME)[0].animeUrl)
        Log.d("Localdb", dao.getAnimeOfCategory(POPULAR_ANIME)[0].imageUrl)
        Log.d("Localdb", dao.getAnimeOfCategory(POPULAR_ANIME)[0].name)
    }

    suspend fun getAllPopularMovies(){
        // added logs for testing purpose
        Log.d("Localdb", dao.getAnimeOfCategory(POPULAR_MOVIE)[0].category)
        Log.d("Localdb", dao.getAnimeOfCategory(POPULAR_MOVIE)[0].animeUrl)
        Log.d("Localdb", dao.getAnimeOfCategory(POPULAR_MOVIE)[0].imageUrl)
        Log.d("Localdb", dao.getAnimeOfCategory(POPULAR_MOVIE)[0].name)
    }

    suspend fun getAllNewSeasons(){
        // added logs for testing purpose
        Log.d("Localdb", dao.getAnimeOfCategory(NEW_SEASON)[0].category)
        Log.d("Localdb", dao.getAnimeOfCategory(NEW_SEASON)[0].animeUrl)
        Log.d("Localdb", dao.getAnimeOfCategory(NEW_SEASON)[0].imageUrl)
        Log.d("Localdb", dao.getAnimeOfCategory(NEW_SEASON)[0].name)
    }

    suspend fun getAllRecentReleases(){
        // added logs for testing purpose
        Log.d("Localdb", dao.getAnimeOfCategory(RECENT_RELEASE)[0].category)
        Log.d("Localdb", dao.getAnimeOfCategory(RECENT_RELEASE)[0].animeUrl)
        Log.d("Localdb", dao.getAnimeOfCategory(RECENT_RELEASE)[0].imageUrl)
        Log.d("Localdb", dao.getAnimeOfCategory(RECENT_RELEASE)[0].name)
    }

}