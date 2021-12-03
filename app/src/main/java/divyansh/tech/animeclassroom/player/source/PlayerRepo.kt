package divyansh.tech.animeclassroom.player.source

import android.util.Log
import divyansh.tech.animeclassroom.common.utils.ResultWrapper
import divyansh.tech.animeclassroom.common.data.api.EpisodeStreamingApi
import divyansh.tech.animeclassroom.cartoons.source.CartoonRemoteRepo
import divyansh.tech.animeclassroom.common.data.home.PlayerScreenModel
import divyansh.tech.animeclassroom.common.utils.Parser.parseEpisodeDetails
import divyansh.tech.animeclassroom.common.utils.Parser.parseStreamingUrl
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PlayerRepo @Inject constructor(
    private val episodeStreamingApi: EpisodeStreamingApi,
    private val cartoonRemoteRepo: CartoonRemoteRepo
){

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
}