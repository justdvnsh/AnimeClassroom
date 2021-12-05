package divyansh.tech.animeclassroom.manga.source

import divyansh.tech.animeclassroom.common.utils.ResultWrapper
import divyansh.tech.animeclassroom.common.data.Manga
import divyansh.tech.animeclassroom.common.data.OfflineMangaModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MangaHomeDefaultRepo @Inject constructor(
    private val remoteRepo: MangaHomeRemoteRepo,
    private val localRepo: MangaHomeLocalRepo
){

    suspend fun getHomePageData(url: String): Flow<ResultWrapper<*>> {
        return flow {
            val remoteResponse = remoteRepo.getHomePage(url)
            if(remoteResponse is ResultWrapper.Success){
                for(manga in remoteResponse.data as ArrayList<Manga>){
                    localRepo.saveMangaDataOffline(manga)
                }
            }

            val localData = localRepo.getAllManga()
            if(localData.isNotEmpty()){
                val response = arrayListOf<Manga>()
                for(data in localData){
                    response.add(convertToMangaModel(data))
                }
                emit(ResultWrapper.Success(response))
            }else{
                emit(ResultWrapper.Error("Manga List is Empty", null))
            }
        }
    }

    private fun convertToMangaModel(data: OfflineMangaModel): Manga {
        return Manga(
            name = data.name,
            mangaUrl = data.mangaUrl,
            imageUrl = data.imageUrl,
            chapterNum = data.chapterNum,
            chapterUrl = data.chapterUrl)
    }

    suspend fun getFeaturedTitles(url: String): Flow<ResultWrapper<*>> {
        return flow {
            val responseFeaturedTitles = remoteRepo.getFeaturedTitles(url)
            emit(responseFeaturedTitles)
        }
    }
}