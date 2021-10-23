package divyansh.tech.animeclassroom.Manga

import divyansh.tech.animeclassroom.ResultWrapper
import divyansh.tech.animeclassroom.mangaModels.Manga
import divyansh.tech.animeclassroom.mangaModels.OfflineMangaModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MangaHomeDefaultRepo @Inject constructor(
    private val remoteRepo: MangaHomeRemoteRepo,
    private val localRepo: MangaHomeLocalRepo
){

    suspend fun getHomePageData(url: String): Flow<ResultWrapper<*>> {
        return flow {
            val localData = localRepo.getAllManga()

            if(localData.isNotEmpty()){
                val response = arrayListOf<Manga>()
                for(data in localData){
                    response.add(convertToMangaModel(data))
                }
                emit(ResultWrapper.Success(response))
            }else{
                val response = remoteRepo.getHomePage(url)
                for(manga in response.data as ArrayList<Manga>){
                    localRepo.saveMangaDataOffline(manga)
                }
                emit(response)
            }
            val response = remoteRepo.getHomePage(url)
            emit(response)
        }
    }

    private fun convertToMangaModel(data: OfflineMangaModel): Manga {
        return Manga(
            name = data.name,
            mangaUrl = data.mangaUrl,
            imageUrl = data.mangaUrl,
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