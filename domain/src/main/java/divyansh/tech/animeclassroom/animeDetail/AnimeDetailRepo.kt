package divyansh.tech.animeclassroom.animeDetail

import android.util.Log
import divyansh.tech.animeclassroom.ResultWrapper
import divyansh.tech.animeclassroom.api.AnimeDetailScreenApi
import divyansh.tech.animeclassroom.favorites.FavoriteAnimeLocalRepo
import divyansh.tech.animeclassroom.models.home.EpisodeModel
import divyansh.tech.animeclassroom.utils.Parser
import javax.inject.Inject

class AnimeDetailRepo @Inject constructor(
    private val animeDetailScreenApi: AnimeDetailScreenApi,
    private val favoriteAnimeLocalRepo: FavoriteAnimeLocalRepo
) {

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
}