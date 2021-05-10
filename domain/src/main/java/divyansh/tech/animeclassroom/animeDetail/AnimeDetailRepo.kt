package divyansh.tech.animeclassroom.animeDetail

import divyansh.tech.animeclassroom.ResultWrapper
import divyansh.tech.animeclassroom.api.AnimeDetailScreenApi
import divyansh.tech.animeclassroom.utils.Parser
import javax.inject.Inject

class AnimeDetailRepo @Inject constructor(
    private val animeDetailScreenApi: AnimeDetailScreenApi
) {

    /*
    * Fetch Anime Details
    * @return ResultWrapper<*>
    * */
    suspend fun getAnimeDetails(url: String): ResultWrapper<*> {
        val response = Parser.parseAnimeDetails(
            animeDetailScreenApi.getAnimeDetails(url)
        )
        return if (response is ResultWrapper.Success) ResultWrapper.Success(response.data)
        else ResultWrapper.Error(message = "No Body To parse", data = null)
    }
}