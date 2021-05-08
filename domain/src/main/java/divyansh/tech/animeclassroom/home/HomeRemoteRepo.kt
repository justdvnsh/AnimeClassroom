package divyansh.tech.animeclassroom.home

import android.util.Log
import divyansh.tech.animeclassroom.ResultWrapper
import divyansh.tech.animeclassroom.api.HomeScreenApi
import divyansh.tech.animeclassroom.utils.Parser.parseGenresAnimeJson
import divyansh.tech.animeclassroom.utils.Parser.parsePopularAnimeJson
import divyansh.tech.animeclassroom.utils.Parser.parseRecentReleaseJson
import okhttp3.ResponseBody
import java.lang.Exception
import javax.inject.Inject

class HomeRemoteRepo @Inject constructor(
    private val homeScreenApi: HomeScreenApi
) {
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
}