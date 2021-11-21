package divyansh.tech.animeclassroom.api

import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Headers
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
//    @Headers(
//        "authority: loadfast1.com",
//        "method: GET",
//        "scheme: https",
//        "accept: */*",
//        "accept-encoding: gzip, deflate, br",
//        "accept-language: en-GB,en-US;q=0.9,en;q=0.8,ca;q=0.7",
//        "origin: https://gogoplay1.com",
//        "referer: https://gogoplay1.com/streaming.php?id=MTc0OTQ1&title=Boruto%3A+Naruto+Next+Generations+Episode+225",
//        "sec-ch-ua: 'Not A;Brand';v='99', 'Chromium';v='96', 'Google Chrome';v='96'",
//        "sec-ch-ua-mobile: ?0",
//        "sec-ch-ua-platform: 'Windows'",
//        "sec-fetch-dest: empty",
//        "sec-fetch-mode: cors",
//        "sec-fetch-site: cross-site",
//        "user-agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/96.0.4664.45 Safari/537.36"
//    )
    @GET
    suspend fun getStreamingUrl(
        @Url url: String
    ): ResponseBody
}