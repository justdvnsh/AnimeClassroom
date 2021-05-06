package divyansh.tech.animeclassroom.home

import android.util.Log
import divyansh.tech.animeclassroom.ResultWrapper
import divyansh.tech.animeclassroom.api.HomeScreenApi
import divyansh.tech.animeclassroom.utils.Parser.parsePopularAnimeJson
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
            homeScreenApi.fetchPopularAnimes().string()
        )
        Log.i("HOME", response.data.toString())
        return if (response is ResultWrapper.Success) ResultWrapper.Success(response.data)
        else ResultWrapper.Error(message = "No Body To parse", data = null)
    }

}