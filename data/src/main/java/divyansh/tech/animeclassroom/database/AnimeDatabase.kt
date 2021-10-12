package divyansh.tech.animeclassroom.database

import androidx.room.Database
import androidx.room.RoomDatabase
import dagger.Provides
import divyansh.tech.animeclassroom.models.home.OfflineAnimeModel
import javax.inject.Inject

@Database(entities = arrayOf(OfflineAnimeModel::class), version = 2)
abstract class AnimeDatabase: RoomDatabase() {
    abstract fun animeDao(): AnimeDao
}