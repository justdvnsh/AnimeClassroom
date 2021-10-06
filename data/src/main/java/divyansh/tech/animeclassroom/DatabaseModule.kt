package divyansh.tech.animeclassroom

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import divyansh.tech.animeclassroom.database.AnimeDatabase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

//    @Provides
//    @Singleton
//    fun providesRealmDatabase(
//        @ApplicationContext context: Context
//    ): Realm {
//        Realm.init(context)
//        val realmConfig = RealmConfiguration.Builder()
//            .name("AnimeClassroom")
//            .allowQueriesOnUiThread(false)
//            .deleteRealmIfMigrationNeeded()
//            .build()
//        Realm.setDefaultConfiguration(realmConfig)
//        return Realm.getDefaultInstance()
//    }

    @Provides
    @Singleton
    fun provideAnimeDatabase(@ApplicationContext context: Context)=
        Room
            .databaseBuilder(context, AnimeDatabase::class.java, "anime-db")
            .fallbackToDestructiveMigration()
            .build()


    @Provides
    @Singleton
    fun providesAnimeDao(db: AnimeDatabase) = db.animeDao()
}