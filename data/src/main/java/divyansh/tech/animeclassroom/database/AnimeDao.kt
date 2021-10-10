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
    fun getAllAnimes(): List<OfflineAnimeModel>

    @Insert
    fun insertAnime(anime: OfflineAnimeModel)

    @Delete
    fun deleteAnime(anime: OfflineAnimeModel)
}