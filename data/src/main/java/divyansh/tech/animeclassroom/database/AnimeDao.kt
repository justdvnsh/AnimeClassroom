package divyansh.tech.animeclassroom.database

import androidx.lifecycle.LiveData
import androidx.room.*
import divyansh.tech.animeclassroom.models.home.OfflineAnimeModel

@Dao
interface AnimeDao {
    @Query("SELECT * FROM animes")
    suspend fun getAllAnimes(): List<OfflineAnimeModel>

    @Query("SELECT * FROM animes WHERE category=:category")
    fun getAnimeOfCategory(category:String): List<OfflineAnimeModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAnime(anime: OfflineAnimeModel)

    @Delete
    suspend fun deleteAnime(anime: OfflineAnimeModel)

    @Query("SELECT EXISTS(SELECT 1 FROM animes WHERE animeUrl = :animeUrl LIMIT 1)")
    suspend fun isAnimeWithUrlSaved(animeUrl: String): Boolean
}