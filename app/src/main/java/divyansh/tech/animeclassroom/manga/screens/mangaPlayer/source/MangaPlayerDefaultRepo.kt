package divyansh.tech.animeclassroom.manga.screens.mangaPlayer.source

import divyansh.tech.animeclassroom.common.utils.ResultWrapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MangaPlayerDefaultRepo @Inject constructor(
    private val remoteRepo: MangaPlayerRemoteRepo
) {

    suspend fun getMangaChapter(url: String): Flow<ResultWrapper<ArrayList<String>>> {
        return flow {
            emit(remoteRepo.getChapterItems(url))
        }
    }
}