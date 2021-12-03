package divyansh.tech.animeclassroom.cartoons.source

import divyansh.tech.animeclassroom.common.utils.C
import divyansh.tech.animeclassroom.common.utils.ResultWrapper
import divyansh.tech.animeclassroom.common.data.cartoonApi.CartoonAPI
import divyansh.tech.animeclassroom.common.data.cartoonModels.Cartoons
import divyansh.tech.animeclassroom.common.data.home.PlayerScreenModel
import divyansh.tech.animeclassroom.common.utils.CartoonParser
import javax.inject.Inject

class CartoonRemoteRepo @Inject constructor(
    private val service: CartoonAPI
) {

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
}