package divyansh.tech.animeclassroom.api

import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Url

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