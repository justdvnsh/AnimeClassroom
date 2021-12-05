package divyansh.tech.animeclassroom.player.source

import android.util.Log
import divyansh.tech.animeclassroom.common.utils.ResultWrapper
import divyansh.tech.animeclassroom.cartoons.source.CartoonRemoteRepo
import divyansh.tech.animeclassroom.common.data.PlayerScreenModel
import divyansh.tech.animeclassroom.common.utils.Parser.parseEpisodeDetails
import divyansh.tech.animeclassroom.common.utils.Parser.parseStreamingUrl
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.ResponseBody
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Url
import javax.inject.Inject

class PlayerRepo @Inject constructor(
    retrofit: Retrofit,
    private val cartoonRemoteRepo: CartoonRemoteRepo
){

    private val episodeStreamingApi = retrofit.create(EpisodeStreamingApi::class.java)

    suspend fun getEpisodeDetails(episodeUrl: String): Flow<ResultWrapper<*>> {
        return flow {
            val response =  parseEpisodeDetails(
                    episodeStreamingApi.getEpisodeDetails(episodeUrl).string()
            )
            if (response is ResultWrapper.Success) {
                Log.i("Player-Repo", "Inside success call ${(response.data as PlayerScreenModel).streamingUrl}")
                val streamingUrl = parseStreamingUrl(
                        response = episodeStreamingApi.getStreamingUrl((response.data as PlayerScreenModel).streamingUrl).string(),
                        playerModel = response.data as PlayerScreenModel
                )
                emit(streamingUrl)
            } else {
                emit(ResultWrapper.Error(message = "Something went wrong", data = null))
            }
        }
    }

    suspend fun getCartoonStreamingUrl(url: String): Flow<ResultWrapper<PlayerScreenModel>> {
        return flow {
            emit(cartoonRemoteRepo.getStreamingUrl(url))
        }
    }

    /*
* Get the episode streaming API
* */
    interface EpisodeStreamingApi {

        /*
        * Function to get the episode details page resposne -> https://www1.gogoanime.ai/one-piece-episode-165
        * */
        @GET
        suspend fun getEpisodeDetails(
            @Url url: String
        ): ResponseBody

        /*
        * Functoin to get the streaming and downloading url -> view-source:https://gogo-play.net/download?id=NDEyOQ==&typesub=Gogoanime-SUB&title=One+Piece+Episode+165
        * */
        @GET
        suspend fun getStreamingUrl(
            @Url url: String
        ): ResponseBody
    }
}