package divyansh.tech.animeclassroom.animeDetail.source

import android.util.Log
import divyansh.tech.animeclassroom.common.utils.ResultWrapper
import divyansh.tech.animeclassroom.favorites.source.FavoriteAnimeLocalRepo
import divyansh.tech.animeclassroom.common.data.EpisodeModel
import divyansh.tech.animeclassroom.common.utils.Parser
import okhttp3.ResponseBody
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Url
import javax.inject.Inject

class AnimeDetailRepo @Inject constructor(
    retrofit: Retrofit,
    private val favoriteAnimeLocalRepo: FavoriteAnimeLocalRepo
) {

    private val animeDetailScreenApi = retrofit.create(AnimeDetailScreenApi::class.java)

    /*
    * Fetch Anime Details
    * @return ResultWrapper<*>
    * */
    suspend fun getAnimeDetails(url: String): ResultWrapper<*> {
        Log.i("HOME-ANIMEREPO", url)
        val isAnimeSaved = favoriteAnimeLocalRepo.isAnimeWithUrlSaved(url)
        val response = Parser.parseAnimeDetails(
            animeDetailScreenApi.getAnimeDetails(url).string(),
            isAnimeSaved
        )
        return if (response is ResultWrapper.Success) ResultWrapper.Success(response.data)
        else ResultWrapper.Error(message = "No Body To parse", data = null)
    }

    /*get tje anime episode list*/
    suspend fun getEpisodeList(endEpisode: Int, animeUrl: String): ResultWrapper<List<EpisodeModel>> {
        val _list: MutableList<EpisodeModel> = mutableListOf()
        for (i in 1 until endEpisode + 1) {
            val model = EpisodeModel(
                episodeNumber = i.toString(),
                episodeUrl = "/${animeUrl.split("/").last()}-episode-$i"
            )
            _list.add(model)
        }
        Log.i("Episodes", animeUrl.split("/").last())
        return ResultWrapper.Success(_list)
    }

    /*
* Interface for the Anime Detail Screen
* */
    interface AnimeDetailScreenApi {

        /*
        * Get the Details for a particular anime
        * */
        @GET
        suspend fun getAnimeDetails(
            @Url url: String
        ): ResponseBody
    }
}