package divyansh.tech.animeclassroom.database

import androidx.room.Database
import androidx.room.RoomDatabase
import divyansh.tech.animeclassroom.mangaModels.OfflineMangaModel
import divyansh.tech.animeclassroom.models.home.OfflineAnimeModel

@Database(entities = [OfflineAnimeModel::class, OfflineMangaModel::class], version = 2)
abstract class CacheDatabase :RoomDatabase(){
    abstract fun cacheDao():CacheDao
}