package divyansh.tech.animeclassroom.api

import okhttp3.Response
import retrofit2.http.GET

/*
* Interface for all the home screen
* */
interface HomeScreenApi {

    /*
    * Function to fetch popular animes and recent releases
    * */
    @GET("/popular.html")
    suspend fun fetchPopularAnimes(): Response
}