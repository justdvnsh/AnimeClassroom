package divyansh.tech.animeclassroom.Manga

import divyansh.tech.animeclassroom.ResultWrapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MangaHomeDefaultRepo @Inject constructor(
    private val remoteRepo: MangaHomeRemoteRepo,
    private val localRepo: MangaHomeLocalRepo
){

    suspend fun getHomePageData(url: String): Flow<ResultWrapper<*>> {
        //TODO: Add local data first. (caching purpose)
        return flow {
            val response = remoteRepo.getHomePage(url)
            emit(response)
        }
    }
}