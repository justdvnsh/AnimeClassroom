package divyansh.tech.animeclassroom.database

import androidx.room.*
import divyansh.tech.animeclassroom.models.home.OfflineAnimeModel

@Dao
interface CacheDao {

    @Query("SELECT * FROM animes WHERE category=:category")
    fun getAnimeOfCategory(category:String): List<OfflineAnimeModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAnime(anime: OfflineAnimeModel)

    @Delete
    suspend fun deleteAnime(anime: OfflineAnimeModel)
}