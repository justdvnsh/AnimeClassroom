package divyansh.tech.animeclassroom.common.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import divyansh.tech.animeclassroom.common.data.OfflineMangaModel
import divyansh.tech.animeclassroom.common.data.OfflineAnimeModel

@Database(entities = [OfflineAnimeModel::class, OfflineMangaModel::class], version = 3)
abstract class CacheDatabase :RoomDatabase(){
    abstract fun cacheDao(): CacheDao
}