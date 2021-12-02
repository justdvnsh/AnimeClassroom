package divyansh.tech.animeclassroom.cartoon

import divyansh.tech.animeclassroom.ResultWrapper
import divyansh.tech.animeclassroom.cartoonModels.Cartoons

interface CartoonDataSource {
    suspend fun getCartoons(): ResultWrapper<ArrayList<Cartoons>>
}