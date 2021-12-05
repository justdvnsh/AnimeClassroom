package divyansh.tech.animeclassroom.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import divyansh.tech.animeclassroom.common.data.database.AnimeDatabase
import divyansh.tech.animeclassroom.common.data.database.CacheDatabase
import divyansh.tech.animeclassroom.common.utils.C
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApplicationDependencies {
    /*
    * Provide retrofit instance
    * */
    @Provides
    @Singleton
    fun provideRetrofitInstance(): Retrofit =
        Retrofit.Builder()
            .baseUrl(C.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

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