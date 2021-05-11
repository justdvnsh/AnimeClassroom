package divyansh.tech.animeclassroom.player

import divyansh.tech.animeclassroom.ResultWrapper
import divyansh.tech.animeclassroom.api.EpisodeStreamingApi
import divyansh.tech.animeclassroom.utils.Parser.parseEpisodeDetails
import javax.inject.Inject

class PlayerRepo @Inject constructor(
    private val episodeStreamingApi: EpisodeStreamingApi
){

    suspend fun getEpisodeDetails(episodeUrl: String): ResultWrapper<*> {
        val response =  parseEpisodeDetails(
            episodeStreamingApi.getEpisodeDetails(episodeUrl).string()
        )
        return if (response is ResultWrapper.Success) ResultWrapper.Success(response.data)
        else ResultWrapper.Error(message = "failed", data = null)
    }

//    suspend fun getStreamingUrl(): ResultWrapper<*> {
//        val response = parseStreamingUrl(
//            episodeStreamingApi.getStreamingUrl(episodeUrl).string()
//        )
//    }
}