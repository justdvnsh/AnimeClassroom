package divyansh.tech.animeclassroom.favorites.source

import android.util.Log
import divyansh.tech.animeclassroom.common.data.database.AnimeDao
import divyansh.tech.animeclassroom.common.data.home.AnimeDetailModel
import divyansh.tech.animeclassroom.common.data.home.OfflineAnimeModel
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

    suspend fun getAllAnime(): List<OfflineAnimeModel> {
        val list = dao.getAllAnimes()
        Log.i("ROOM REPO -> ", list.toString())
        return list
    }

    suspend fun isAnimeWithUrlSaved(animeUrl: String) = dao.isAnimeWithUrlSaved(animeUrl)
}