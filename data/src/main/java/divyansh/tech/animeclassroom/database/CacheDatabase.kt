package divyansh.tech.animeclassroom.database

import androidx.room.Database
import androidx.room.RoomDatabase
import divyansh.tech.animeclassroom.models.home.OfflineAnimeModel

@Database(entities = arrayOf(OfflineAnimeModel::class), version = 2)
abstract class CacheDatabase :RoomDatabase(){
    abstract fun cacheDao():CacheDao
}