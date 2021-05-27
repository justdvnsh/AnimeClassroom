package divyansh.tech.animeclassroom

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import divyansh.tech.animeclassroom.C.BASE_URL
import divyansh.tech.animeclassroom.api.AnimeDetailScreenApi
import divyansh.tech.animeclassroom.api.AnimeSearchApi
import divyansh.tech.animeclassroom.api.EpisodeStreamingApi
import divyansh.tech.animeclassroom.api.HomeScreenApi
import divyansh.tech.animeclassroom.mangaApi.MangaHomeApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/*
* Dependency container for the Networking facilities
* */
@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    /*
    * Provide retrofit instance
    * */
    @Provides
    @Singleton
    fun provideRetrofitInstance(): Retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    /*
    * Provides homeScreenApi
    * @param retrofit: Retrofit
    * @returns -> HomeScreenApi service
    * */
    @Provides
    @Singleton
    fun providesHomeScreenApi(
        retrofit: Retrofit
    ): HomeScreenApi = retrofit.create(HomeScreenApi::class.java)

    /*
    * Provides animeDetailScreenApi
    * @param retrofit: Retrofit
    * @returns -> AnimeDetailScreenApi service
    * */
    @Provides
    @Singleton
    fun providesAnimeDetailScreenApi(
        retrofit: Retrofit
    ): AnimeDetailScreenApi = retrofit.create(AnimeDetailScreenApi::class.java)

    /*
    * Provides EpisodeStreamingapi
    * @param retrofit: Retrofit
    * @returns -> EpisodeStreamingapi service
    * */
    @Provides
    @Singleton
    fun providesEpisodeStreamingApi(
        retrofit: Retrofit
    ): EpisodeStreamingApi = retrofit.create(EpisodeStreamingApi::class.java)

    /*
    * Provides AnimeSearchapi
    * @param retrofit: Retrofit
    * @returns -> AnimeSearchapi service
    * */
    @Provides
    @Singleton
    fun providesAnimeSearchApi(
            retrofit: Retrofit
    ): AnimeSearchApi = retrofit.create(AnimeSearchApi::class.java)

    /*
    * Provides MangaHomeApi
    * @param retrofit: Retrofit
    * @returns -> MangaHomeApi
    * */
    @Provides
    @Singleton
    fun providesMangaHomeApi(
        retrofit: Retrofit
    ): MangaHomeApi = retrofit.create(MangaHomeApi::class.java)
}