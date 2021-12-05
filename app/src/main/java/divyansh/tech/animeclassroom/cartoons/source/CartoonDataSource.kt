package divyansh.tech.animeclassroom.cartoons.source

import divyansh.tech.animeclassroom.common.utils.ResultWrapper
import divyansh.tech.animeclassroom.common.data.Cartoons

interface CartoonDataSource {
    suspend fun getCartoons(): ResultWrapper<ArrayList<Cartoons>>
}