package divyansh.tech.animeclassroom.cartoon

import divyansh.tech.animeclassroom.C
import divyansh.tech.animeclassroom.ResultWrapper
import divyansh.tech.animeclassroom.cartoonApi.CartoonAPI
import divyansh.tech.animeclassroom.cartoonModels.Cartoons
import divyansh.tech.animeclassroom.models.home.PlayerScreenModel
import divyansh.tech.animeclassroom.utils.CartoonParser
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