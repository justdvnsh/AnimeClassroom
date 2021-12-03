package divyansh.tech.animeclassroom.common.data

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import divyansh.tech.animeclassroom.common.data.database.AnimeDatabase
import divyansh.tech.animeclassroom.common.data.database.CacheDatabase
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

    @Provides
    @Singleton
    fun provideCacheDatabase(@ApplicationContext context: Context)=
        Room
            .databaseBuilder(context, CacheDatabase::class.java,"cache-db")
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    @Singleton
    fun providesCacheDao(db: CacheDatabase) = db.cacheDao()

}