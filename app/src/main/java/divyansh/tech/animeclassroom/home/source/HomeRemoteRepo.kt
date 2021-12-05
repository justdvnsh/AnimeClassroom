package divyansh.tech.animeclassroom.home.source

import divyansh.tech.animeclassroom.common.utils.ResultWrapper
import divyansh.tech.animeclassroom.common.utils.Parser.parseGenresAnimeJson
import divyansh.tech.animeclassroom.common.utils.Parser.parsePopularAnimeJson
import divyansh.tech.animeclassroom.common.utils.Parser.parseRecentReleaseJson
import okhttp3.ResponseBody
import retrofit2.Retrofit
import retrofit2.http.GET
import javax.inject.Inject

class HomeRemoteRepo @Inject constructor(
    retrofit: Retrofit
) {

    private val homeScreenApi = retrofit.create(HomeScreenApi::class.java)

    /*
    * Fetch popular animes remotely
    * @return ResultWrapper<*>
    * */
    suspend fun getPopularAnimes(): ResultWrapper<*> {
        val response = parsePopularAnimeJson(
            homeScreenApi.fetchHomeScreenData().string()
        )
        return if (response is ResultWrapper.Success) ResultWrapper.Success(response.data)
        else ResultWrapper.Error(message = "No Body To parse", data = null)
    }

    /*
    * Fetch the recent releases
    * @returns ResultWrapper<*>
    * */
    suspend fun getRecentReleases(): ResultWrapper<*> {
        val response = parseRecentReleaseJson(
            homeScreenApi.fetchHomeScreenData().string()
        )
        return if (response is ResultWrapper.Success) ResultWrapper.Success(response.data)
        else ResultWrapper.Error(message = "No Body To parse", data = null)
    }

    /*
    * Fetch the popular movies
    * @returns ResultWrapper<*>
    * */
    suspend fun getPopularMovies(): ResultWrapper<*> {
        val response = parsePopularAnimeJson(
                homeScreenApi.fetchPopularMovies().string()
        )
        return if (response is ResultWrapper.Success) ResultWrapper.Success(response.data)
        else ResultWrapper.Error(message = "No Body to Parse", data = null)
    }

    /*
    * Fetch the new seasons
    * @returns ResultWrapper<*>
    * */
    suspend fun getNewSeasons(): ResultWrapper<*> {
        val response = parsePopularAnimeJson(
                homeScreenApi.fetchNewSeasons().string()
        )
        return if (response is ResultWrapper.Success) ResultWrapper.Success(response.data)
        else ResultWrapper.Error(message = "No Body to Parse", data = null)
    }

    /*
    * Fetch the genres
    * @returns ResultWrapper<*>
    * */
    suspend fun getGenres(): ResultWrapper<*> {
        val response = parseGenresAnimeJson(
                homeScreenApi.fetchNewSeasons().string()
        )
        return if (response is ResultWrapper.Success) ResultWrapper.Success(response.data)
        else ResultWrapper.Error(message = "No Body to Parse", data = null)
    }

    /*
* Interface for all the home screen
* */
    interface HomeScreenApi {

        /*
        * Function to fetch popular animes and recent releases
        * */
        @GET("/popular.html")
        suspend fun fetchHomeScreenData(): ResponseBody

        /*
        * Function to fetch popular movies
        * */
        @GET("/anime-movies.html")
        suspend fun fetchPopularMovies(): ResponseBody

        /*
        * Function to fetch new seasons
        * */
        @GET("/new-season.html")
        suspend fun fetchNewSeasons(): ResponseBody

        /*
        * Fetch genres
        * */
        @GET("")
        suspend fun fetchGenres(): ResponseBody
    }
}