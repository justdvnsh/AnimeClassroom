package divyansh.tech.animeclassroom.manga.source

import divyansh.tech.animeclassroom.common.data.database.CacheDao
import divyansh.tech.animeclassroom.common.data.Manga
import divyansh.tech.animeclassroom.common.data.OfflineMangaModel
import javax.inject.Inject

class MangaHomeLocalRepo @Inject constructor(
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