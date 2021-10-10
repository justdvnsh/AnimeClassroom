package divyansh.tech.animeclassroom.mangaDetail

import divyansh.tech.animeclassroom.ResultWrapper
import divyansh.tech.animeclassroom.mangaModels.MangaDetail
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MangaDetailDefaultRepo @Inject constructor(
    private val remoteRepo: MangaDetailRemoteRepo
) {

    suspend fun getMangaDetails(url: String): Flow<ResultWrapper<MangaDetail>> {
        return flow {
            emit(remoteRepo.getMangaDetail(url))
        }
    }
}