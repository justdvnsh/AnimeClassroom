package divyansh.tech.animeclassroom.common.data.database

import androidx.room.*
import divyansh.tech.animeclassroom.common.data.OfflineMangaModel
import divyansh.tech.animeclassroom.common.data.OfflineAnimeModel

@Dao
interface CacheDao {

    /*************** ANIME *******************/

    @Query("SELECT * FROM animes WHERE category=:category")
    fun getAnimeOfCategory(category:String): List<OfflineAnimeModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAnime(anime: OfflineAnimeModel)

    @Delete
    suspend fun deleteAnime(anime: OfflineAnimeModel)


    /*************** MANGA ********************/

    @Query("SELECT * FROM manga")
    suspend fun getAllManga(): List<OfflineMangaModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertManga(manga: OfflineMangaModel)

    @Delete
    suspend fun deleteManga(manga: OfflineMangaModel)

}