package divyansh.tech.animeclassroom.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import divyansh.tech.animeclassroom.models.home.OfflineAnimeModel

@Dao
interface AnimeDao {
    @Query("SELECT * FROM animes")
    suspend fun getAllAnimes(): List<OfflineAnimeModel>

    @Insert
    suspend fun insertAnime(anime: OfflineAnimeModel)

    @Delete
    suspend fun deleteAnime(anime: OfflineAnimeModel)

    @Query("SELECT EXISTS(SELECT 1 FROM animes WHERE animeUrl = :animeUrl LIMIT 1)")
    suspend fun isAnimeWithUrlSaved(animeUrl: String): Boolean
}