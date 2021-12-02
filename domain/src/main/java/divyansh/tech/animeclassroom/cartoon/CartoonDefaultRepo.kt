package divyansh.tech.animeclassroom.cartoon

import divyansh.tech.animeclassroom.ResultWrapper
import divyansh.tech.animeclassroom.cartoonModels.Cartoons
import javax.inject.Inject

class CartoonDefaultRepo @Inject constructor(
    private val remoteRepo: CartoonRemoteRepo
) {

    suspend fun getCartoons(): ResultWrapper<ArrayList<Cartoons>> = remoteRepo.getCartoons()

    suspend fun getEpisodes(url: String) = remoteRepo.getCartoons(url)

    suspend fun getStreamingUrl(url: String) = remoteRepo.getStreamingUrl(url)
}