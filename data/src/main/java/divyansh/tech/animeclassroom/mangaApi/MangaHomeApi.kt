package divyansh.tech.animeclassroom.mangaApi

import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Url

/*\
* Get the interface functions for the home page
* */
interface MangaHomeApi {

    /*
    * Fethces the home page
    * */
    @GET
    suspend fun fetchHomePage(
        @Url url: String
    ) : ResponseBody
}