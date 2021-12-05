package divyansh.tech.animeclassroom.cartoons.source

import divyansh.tech.animeclassroom.common.utils.C
import divyansh.tech.animeclassroom.common.utils.ResultWrapper
import divyansh.tech.animeclassroom.common.data.Cartoons
import divyansh.tech.animeclassroom.common.data.PlayerScreenModel
import divyansh.tech.animeclassroom.common.utils.CartoonParser
import okhttp3.ResponseBody
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Url
import javax.inject.Inject

class CartoonRemoteRepo @Inject constructor(
    retrofit: Retrofit
) {

    private val service  = retrofit.create(CartoonAPI::class.java)

    suspend fun getCartoons(): ResultWrapper<ArrayList<Cartoons>> {
        val response = service.fetchCartoons(C.CARTOON_URL + "cartoon-series/series/")
        return CartoonParser.parseCartoons(response.string())
    }

    suspend fun getCartoons(url: String): ResultWrapper<ArrayList<Cartoons>> {
        val response = service.fetchCartoons(url)
        return CartoonParser.parseCartoons(response.string())
    }

    suspend fun getStreamingUrl(url: String): ResultWrapper<PlayerScreenModel> {
        val response = service.fetchCartoonEpisodeURL(url)
        return CartoonParser.parseStreamingUrl(response.string())
    }

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
}