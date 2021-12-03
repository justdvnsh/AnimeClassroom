package divyansh.tech.animeclassroom.common.data.cartoonApi

import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Url

interface CartoonAPI {

    @GET
    suspend fun fetchCartoons(
        @Url url: String
    ): ResponseBody

    @GET
    suspend fun fetchCartoonEpisodeList(
        @Url url: String
    )

    @GET
    suspend fun fetchCartoonEpisodeURL(
        @Url url: String
    ): ResponseBody
}