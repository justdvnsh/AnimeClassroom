package divyansh.tech.animeclassroom.player

import android.util.Log
import divyansh.tech.animeclassroom.ResultWrapper
import divyansh.tech.animeclassroom.api.EpisodeStreamingApi
import divyansh.tech.animeclassroom.models.home.PlayerScreenModel
import divyansh.tech.animeclassroom.utils.Parser.parseEpisodeDetails
import divyansh.tech.animeclassroom.utils.Parser.parseStreamingUrl
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PlayerRepo @Inject constructor(
    private val episodeStreamingApi: EpisodeStreamingApi
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

//    suspend fun getStreamingUrl(): ResultWrapper<*> {
//        val response = parseStreamingUrl(
//            episodeStreamingApi.getStreamingUrl(episodeUrl).string()
//        )
//    }
}