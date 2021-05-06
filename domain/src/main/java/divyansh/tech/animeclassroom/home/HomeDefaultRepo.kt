package divyansh.tech.animeclassroom.home

import android.util.Log
import divyansh.tech.animeclassroom.ResultWrapper
import divyansh.tech.animeclassroom.api.HomeScreenApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class HomeDefaultRepo @Inject constructor(
    private val remoteRepo: HomeRemoteRepo,
    private val localRepo: HomeLocalRepo
) {

    suspend fun parsePopularAnimes(): Flow<ResultWrapper<*>> {
        //TODO: Add local data first. (caching purpose)
        return flow {
            val response = remoteRepo.getPopularAnimes()
            if (response is ResultWrapper.Success) {
                emit(response)
            } else {
                emit(response)
            }
        }
    }
}