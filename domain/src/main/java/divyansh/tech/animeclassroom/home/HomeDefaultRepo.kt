package divyansh.tech.animeclassroom.home

import divyansh.tech.animeclassroom.ResultWrapper
import divyansh.tech.animeclassroom.api.HomeScreenApi
import javax.inject.Inject

class HomeDefaultRepo @Inject constructor(
    private val homeScreenApi: HomeScreenApi
) {

    suspend fun parsePopularAnimes(): ResultWrapper<>
}