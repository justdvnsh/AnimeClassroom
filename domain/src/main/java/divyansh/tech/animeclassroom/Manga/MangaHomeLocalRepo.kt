package divyansh.tech.animeclassroom.Manga

import divyansh.tech.animeclassroom.database.CacheDao
import divyansh.tech.animeclassroom.mangaApi.MangaHomeApi
import divyansh.tech.animeclassroom.mangaModels.Manga
import divyansh.tech.animeclassroom.mangaModels.OfflineMangaModel
import javax.inject.Inject

class MangaHomeLocalRepo @Inject constructor(
    private val mangaHomeApi: MangaHomeApi,
    private val dao: CacheDao
) {

    fun saveMangaDataOffline(manga: Manga){
        val mangaModel = OfflineMangaModel(
            imageUrl = manga.imageUrl,
            name = manga.name,
            mangaUrl = manga.mangaUrl,
            chapterNum = manga.chapterNum,
            chapterUrl = manga.chapterUrl
        )
        dao.insertManga(mangaModel)
    }

    suspend fun getAllManga(): List<OfflineMangaModel>{
        return dao.getAllManga()
    }

    suspend fun deleteManga(manga: OfflineMangaModel){
        dao.deleteManga(manga)
    }

    //TODO: Implement getFeaturedTitle()
}