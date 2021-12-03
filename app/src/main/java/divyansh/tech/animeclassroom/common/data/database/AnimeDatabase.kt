package divyansh.tech.animeclassroom.common.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import divyansh.tech.animeclassroom.common.data.home.OfflineAnimeModel

@Database(entities = arrayOf(OfflineAnimeModel::class), version = 3)
abstract class AnimeDatabase: RoomDatabase() {
    abstract fun animeDao(): AnimeDao
}