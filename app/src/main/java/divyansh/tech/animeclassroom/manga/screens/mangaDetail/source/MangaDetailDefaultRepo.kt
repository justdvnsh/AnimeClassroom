package divyansh.tech.animeclassroom.manga.screens.mangaDetail.source

import divyansh.tech.animeclassroom.common.utils.ResultWrapper
import divyansh.tech.animeclassroom.common.data.mangaModels.MangaDetail
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