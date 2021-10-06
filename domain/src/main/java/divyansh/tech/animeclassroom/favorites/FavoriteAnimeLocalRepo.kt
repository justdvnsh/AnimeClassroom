package divyansh.tech.animeclassroom.favorites

import android.util.Log
import divyansh.tech.animeclassroom.ResultWrapper
import divyansh.tech.animeclassroom.database.AnimeDao
import divyansh.tech.animeclassroom.models.home.AnimeDetailModel
import divyansh.tech.animeclassroom.models.home.GenreModel
import divyansh.tech.animeclassroom.models.home.OfflineAnimeModel
import javax.inject.Inject

class FavoriteAnimeLocalRepo @Inject constructor(
    private val dao: AnimeDao
) {
    suspend fun saveAnime(anime: AnimeDetailModel, animeUrl: String) {
        val model = OfflineAnimeModel(
            name = anime.name,
            imageUrl = anime.imageUrl,
            animeUrl = animeUrl
        )
        dao.insertAnime(model)
    }

    suspend fun getAllAnime() {
        val list = dao.getAllAnimes()
        Log.i("ROOM REPO -> ", list.toString())
    }
}