package divyansh.tech.animeclassroom.Manga

import android.util.Log
import divyansh.tech.animeclassroom.ResultWrapper
import divyansh.tech.animeclassroom.mangaApi.MangaHomeApi
import divyansh.tech.animeclassroom.utils.MangaParser.parseFeaturedTitles
import divyansh.tech.animeclassroom.utils.MangaParser.parseHomePageData
import javax.inject.Inject

class MangaHomeRemoteRepo @Inject constructor(
    private val mangaHomeApi: MangaHomeApi
){

    /*
    * get the home page data
    * */
    suspend fun getHomePage(url: String): ResultWrapper<*> {
        Log.i("MANGA", "INSIDE REMOTE REPO")
        return parseHomePageData(
            mangaHomeApi.fetchHomePage(url).string()
        )
    }

    /*
    * get the featured titles data
    * */
    suspend fun getFeaturedTitles(url: String): ResultWrapper<*> {
        return parseFeaturedTitles(
            mangaHomeApi.fetchHomePage(url).string()
        )
    }
}